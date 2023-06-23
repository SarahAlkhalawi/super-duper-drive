package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NotesMapper notesMapper;

    public NoteService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }



    public Integer addNote(Note note,Integer userid){
        return notesMapper.insertNote(new Note(note.getNotetitle(),note.getNotedescription(),userid));
    }

//    public Integer addNoteOrEditNote(Note note, Integer userid){
//
//        if (note.getNoteid() == null){
//            return notesMapper.insertNote(new Note(note.getNotetitle(),note.getNotedescription(),userid));
//        }
//        else return notesMapper.updateNote(note);
//
//    }

    public Integer updateNote(Note note){
        return notesMapper.updateNote(note);
    }

    public Integer deleteNote(Integer noteid){
        return notesMapper.deleteNote(noteid);
    }

    public List<Note> getNotes(Integer userid){
        List<Note> notes = notesMapper.getNotes(userid);
        return notes;
    }

}
