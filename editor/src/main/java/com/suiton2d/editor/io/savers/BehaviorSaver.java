package com.suiton2d.editor.io.savers;

import com.suiton2d.components.Behavior;
import com.suiton2d.editor.io.FullBufferedWriter;

import java.io.IOException;

public class BehaviorSaver extends BaseComponentSaver<Behavior> {

    public BehaviorSaver(Behavior component) {
        super(component);
    }

    @Override
    public void onSave(FullBufferedWriter fw) throws IOException {
        fw.writeLine(getComponent().getFilename());
    }
}
