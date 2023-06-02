package com.example.mainfeddback;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.mainfeddback.R;
public class ExampleDialog extends AppCompatDialogFragment {

    private int layoutResourceId;

    public ExampleDialog(int layoutResourceId){
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(layoutResourceId, null);
        builder.setView(dialogView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle negative button click event here
                        if (layoutResourceId == R.layout.feedback) {
                            Toast.makeText(getActivity(), "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
                        }else if(layoutResourceId == R.layout.menu){
                            Toast.makeText(getActivity(), "Menu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return builder.create();
    }
}
