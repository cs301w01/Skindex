package com.cs301w01.meatload.activities;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.View;
import com.cs301w01.meatload.R;
import com.cs301w01.meatload.adapters.AlbumAdapter;
import com.cs301w01.meatload.adapters.TagAdapter;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.controllers.PictureManager;
import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.Tag;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Takes a picture and displays it in exploded view along with important metadata including tags, 
 * date, etc.
 * <p>
 * Gives the user an exploded view of the picture being edited.
 * <p>
 * Allows user to change certain metadata such as tags and album.
 * @author Blake Bouchard
 */
public class EditPictureActivity extends Skindactivity {

	private MainManager mainManager;
	private PictureManager pictureManager;
	private Picture picture;
	private ListView tagListView;
	private EditText pictureNameEditText;
	private ImageView pictureView;

	@Override
	public void update(Object model) {
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_picture);
		
		// Set up MainManager
		mainManager = new MainManager();
		mainManager.setContext(this);
		
		// Get picture object from Intent's extras bundle
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		picture = (Picture) extras.getSerializable("picture");
        
		// Set up a new PictureManager using the Picture object passed via the intent 
		pictureManager = new PictureManager(this, picture);

		// TODO: DELETE THESE LINES WHEN picture.getTags() is working!!!
		ArrayList<Tag> testTagList = new ArrayList<Tag>();
		testTagList.add(new Tag("brown", 0));
		testTagList.add(new Tag("moldy", 0));
		testTagList.add(new Tag("bruise", 0));
		testTagList.add(new Tag("taco sauce", 0));
		testTagList.add(new Tag("herp", 0));
		testTagList.add(new Tag("derp", 0));
		//END DELETE, AND UPDATE ARGS IN FOLLOWING FUNCTION CALL
		
		populateTextFields();

		createListeners();
	}
	
	@Override
    protected void onResume() {
    	super.onResume();
    	pictureManager.setContext(this);
    }
	
	/**
	 * Fills the text and image fields on the screen with a current picture.
	 * @see <a href="http://codehenge.net/blog/2011/05/customizing-android-listview-item-layout/">
	 http://codehenge.net/blog/2011/05/customizing-android-listview-item-layout/</a>
	 */
	protected void populateTextFields() {
		pictureView = (ImageView) findViewById(R.id.pictureView);
		pictureView.setImageDrawable(Drawable.createFromPath(picture.getPath()));
		
		pictureNameEditText = (EditText) findViewById(R.id.pictureNameEditText);
		pictureNameEditText.setText(picture.getName());
		
		// Set dateView to toString representation of Date in Picture object
		TextView dateView = (TextView) findViewById(R.id.dateView);
		dateView.setText(picture.getDate().toString());
		
		// Set albumView to string representation of Album in Picture object
		// TODO: This spinner also needs to be populated with other albums in drop down!!
		Spinner albumView = (Spinner) findViewById(R.id.albumView);
		ArrayList<Album> allAlbums = mainManager.getAllAlbums(); 
		albumView.setAdapter(new AlbumAdapter(this, R.layout.list_item, allAlbums));
		albumView.setTag(picture.getAlbumName());
		
		tagListView = (ListView) findViewById(R.id.picTagList);
		Iterator<Tag> tagIter = picture.getTags().iterator();
		ArrayList<Tag> tagList = new ArrayList<Tag>();
		
		while (tagIter.hasNext()) {
			tagList.add(tagIter.next());
		}
		
		TagAdapter arrAdapt = new TagAdapter(this, R.layout.tag_list_item, tagList);
		tagListView.setAdapter(arrAdapt);
		
	}

	protected void createListeners() {
		// Send Email Button logic
        Button sendEmailButton = (Button) findViewById(R.id.sendEmailButton);
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openSendEmailActivity();
            }
        });

        // Add Tag field logic
        final AutoCompleteTextView addTagEditText = 
        		(AutoCompleteTextView) findViewById(R.id.addTagEditText);
        
        // Add Tag button logic
        Button addTagButton = (Button) findViewById(R.id.addTagButton);
        addTagButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pictureManager.addTag(addTagEditText.getText().toString());
                populateTextFields();
            }
        });
        
        // Tag List Long Click Listener logic
        tagListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, 
					long id) {
				deleteTagAlert(position);
				return true;
			}
        	
        });
        
        // Delete Button logic
        Button deletePicButton = (Button) findViewById(R.id.deletePictureButton);
        deletePicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deletePicture();
            }
        });

        // Save Picture logic
        Button savePictureButton = (Button) findViewById(R.id.savePictureButton);
        savePictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                savePicture();
            }
        });
	}

    /**
     * This method implements the logic that occurs when the "savePicture" button is clicked.
     * Checks for any changes in the pictures meta-data, updates those, and then finished the
     * activity.
     */
    private void savePicture() {
        
        //TODO: Check for changes in any of the photo data
        
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Picture Saved.");

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });

        alert.show();
        
    }
    
    /**
     * @deprecated
     */
	@SuppressWarnings("unused")
	private void openEditTagsDialog() {
		
		// Set up Dialog object
		final Dialog editTagsDialog = new Dialog(this);
		editTagsDialog.setContentView(R.layout.edit_tags);
		editTagsDialog.setCancelable(true);
		
		// Populate tags list
		final ArrayList<Tag> allTags = mainManager.getAllTags();
		TagAdapter adapter = new TagAdapter(this, R.layout.tag_list_item, allTags);
		
		// Set up ListView of all Tags
		final ListView tagListView = (ListView) findViewById(R.id.editTagsListView);
		tagListView.setAdapter(adapter);
		tagListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		ArrayList<Tag> pictureTags = (ArrayList<Tag>) picture.getTags();
		
		for (Tag picTag : pictureTags) {
			for (int i = 0; i < allTags.size(); i++) {
				if (picTag.getName().equals(allTags.get(i).getName())) {
					tagListView.setItemChecked(i, true);
				}
			}
		}
		
		Button saveTagsButton = (Button) editTagsDialog.findViewById(R.id.saveTagsButton);
		saveTagsButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				ArrayList<Tag> newTags = new ArrayList<Tag>();
				SparseBooleanArray checkPositions = tagListView.getCheckedItemPositions();
				
				for (int i = 0; i < checkPositions.size(); i++) {
					if (checkPositions.get(i)) {
						newTags.add(allTags.get(i));
					}
				}
				
				pictureManager.setTags(newTags);
				
				editTagsDialog.dismiss();
			}
		});
		
		Button cancelDialogButton = (Button) editTagsDialog.findViewById(R.id.cancelDialogButton);
		cancelDialogButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				editTagsDialog.dismiss();
			}
		});
		
		editTagsDialog.show();
	}

    private void deletePicture() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Delete Picture?");
        alert.setMessage("Are you sure?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                pictureManager.deletePicture();
                finish();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });

        alert.show();

    }
    
    private void deleteTagAlert(final int position) {
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Delete Tag?");
        alert.setMessage("Are you sure?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                pictureManager.deleteTag(picture.getTags().get(position));
                finish();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });

        alert.show();
    }

    private void openSendEmailActivity() {
        Intent sendEmail = new Intent();
        sendEmail.setClassName("com.cs301w01.meatload", 
        		"com.cs301w01.meatload.activities.SendEmailActivity");
        sendEmail.putExtra("picture", picture);
        
        startActivity(sendEmail);
    }
}
