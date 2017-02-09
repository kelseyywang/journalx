package com.example.kelseywang.journalx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import stanford.androidlib.*;

import java.io.File;
import java.io.PrintStream;
import java.util.*;

public class AllEntriesActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_entries);
        setList();
        $LV(R.id.thought_list).setOnItemClickListener(this);
        $LV(R.id.thought_list).setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(ListView list, int index) {
        String thoughtClicked = list.getItemAtPosition(index).toString();
        Intent goToEntry = new Intent(this, OldEntryActivity.class);
        goToEntry.putExtra("thoughtClicked", thoughtClicked);
        startActivity(goToEntry);
    }

    //This list has questions and answers--one question and answer
    //per line, separated by a tab. Two consecutive lines form
    //questions and answers for one single day
    private List<String> getList() {
        List<String> thoughtArraylist = new ArrayList<>();
        String q1, a1, q2, a2;
        String mc, dc, yc,
                mm, dm, ym;
        try {
            Scanner scanner = new Scanner(openFileInput("thoughtsList.txt")).useDelimiter("\\t|\\n");
            while (scanner.hasNext()) {
                q1 = scanner.next();
                a1 = scanner.next();
                q2 = scanner.next();
                a2 = scanner.next();
                mc = scanner.next();
                dc = scanner.next();
                yc = scanner.next();
                mm = scanner.next();
                dm = scanner.next();
                ym = scanner.next();
                thoughtArraylist.add(q1 + "\t" + a1 + "\n" + q2 + "\t" + a2 + "\n"
                        + mc + "\t" + dc + "\t" + yc + "\t"
                        + mm + "\t" + dm + "\t" + ym);
            }
        } catch (Exception e) {
            // do nothing
        }
        return thoughtArraylist;
    }

    //Questions list has just questions--one per line, and two
    //consecutive ones forming the questions for one single day
    private List<String> getQuestionsList() {
        List<String> thoughtArraylist = new ArrayList<>();
        String q1, a1, q2, a2;
        String mc, dc, yc,
                mm, dm, ym;
        try {
            Scanner scanner = new Scanner(openFileInput("thoughtsList.txt")).useDelimiter("\\t|\\n");
            while (scanner.hasNext()) {
                q1 = scanner.next();
                a1 = scanner.next();
                q2 = scanner.next();
                a2 = scanner.next();
                mc = scanner.next();
                dc = scanner.next();
                yc = scanner.next();
                mm = scanner.next();
                dm = scanner.next();
                ym = scanner.next();
                thoughtArraylist.add(q1 + "\n" + q2);
            }
        } catch (Exception e) {
            // do nothing
        }
        return thoughtArraylist;
    }

    //Sets list with simplelist
    private void setList() {
        SimpleList.with(this).setItems(R.id.thought_list, getQuestionsList());
    }

    //Deletes items when thoughts are long clicked
    //and updates the list with helper function removeLineFromFile
    @Override
    public boolean onItemLongClick(ListView list, int index) {
        String thoughtLongClicked = list.getItemAtPosition(index).toString();
        List<String> questionsArraylist = getQuestionsList();
        List<String> thoughtsArraylist = getList();
        removeLineFromFile(questionsArraylist, thoughtsArraylist, thoughtLongClicked);
        setList();
        return true;
    }

    //Uses a temporary file to delete an item from the
    //thought list by recreating the remaining file
    //from scratch without deleted item
    private void removeLineFromFile(List<String> questionsArraylist, List<String> thoughtsArraylist, String thoughtToDelete) {
        PrintStream tempOutput = new PrintStream(openFileOutput("temp_thoughts.txt", MODE_APPEND));
        for (int i = 0; i < questionsArraylist.size(); i++) {
            if (questionsArraylist.get(i).equals(thoughtToDelete)) {
                questionsArraylist.remove(i);
                thoughtsArraylist.remove(i);
                break;
            }
        }
        for (int i = 0; i < thoughtsArraylist.size(); i++) {
            tempOutput.println(thoughtsArraylist.get(i));
        }
        tempOutput.close();
        File filesDirectory = getFilesDir();
        File tempFile = new File(filesDirectory, "temp_thoughts.txt");
        File oldFile = new File(filesDirectory, "thoughtsList.txt");
        tempFile.renameTo(oldFile);
    }

}