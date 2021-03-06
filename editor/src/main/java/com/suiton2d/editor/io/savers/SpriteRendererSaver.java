package com.suiton2d.editor.io.savers;

import com.badlogic.gdx.utils.Array;
import com.suiton2d.components.Animation;
import com.suiton2d.components.KeyFrameAnimation;
import com.suiton2d.components.SpriteRenderer;
import com.suiton2d.editor.io.FullBufferedWriter;
import com.suiton2d.editor.io.Types;

import java.io.IOException;

public class SpriteRendererSaver extends BaseComponentSaver<SpriteRenderer> {

    public SpriteRendererSaver(SpriteRenderer component) {
        super(component);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onSave(FullBufferedWriter fw) throws IOException {
        fw.writeLine(Types.RendererType.SPRITE.name());
        fw.writeLine(getComponent().getFilename());
        fw.writeIntLine(getComponent().getAnimations().size);
        for (Animation anim : (Array<Animation>) getComponent().getAnimations()) {
            if (anim instanceof KeyFrameAnimation) {
                fw.writeLine(Types.AnimationType.KEY_FRAME.name());
                KeyFrameAnimation keyFrameAnimation = (KeyFrameAnimation) anim;
                fw.writeLine(keyFrameAnimation.getName());
                fw.writeIntLine(keyFrameAnimation.getSpriteSheet().getFrameWidth());
                fw.writeIntLine(keyFrameAnimation.getSpriteSheet().getFrameHeight());
                fw.writeIntLine(keyFrameAnimation.getStartFrame());
                fw.writeIntLine(keyFrameAnimation.getEndFrame());
                fw.writeFloatLine(keyFrameAnimation.getSpeed());
                fw.writeBoolLine(keyFrameAnimation.isWrap());
            }
        }

        fw.writeIntLine(getComponent().getCurrentAnimationIndex());
    }
}
