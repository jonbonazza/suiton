package com.nebula2d.editor.ui;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.nebula2d.editor.framework.GameObject;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class RenderCanvas extends LwjglAWTCanvas implements MouseListener, MouseMotionListener{

    protected float camXOffset, camYOffset;

    protected RenderAdapter adapter;

    protected Point lastPoint;

    protected boolean isMouseDown;

    public RenderCanvas(RenderAdapter adapter) {
        super(adapter, true);
        this.adapter = adapter;
        this.getCanvas().addMouseListener(this);
        this.getCanvas().addMouseMotionListener(this);
        this.isMouseDown = false;
        //this.getCanvas().setMinimumSize(new Dimension(600,600));
    }

    protected List<GameObject> getSelectedGameObjects(int x, int y) {

        List<GameObject> res = null;
        //TODO: finish this method

        return res;
    }

    protected void translateObject(Point mousePos) {
        int dx = mousePos.x - lastPoint.x;
        int dy = mousePos.y - lastPoint.y;

        float newX = adapter.getSelectedObject().getPosition().x + dx;
        float newY = adapter.getSelectedObject().getPosition().y - dy;
        adapter.getSelectedObject().setPosition(newX, newY);
    }

    protected void scaleObject(Point mousePos) {
        int dx = mousePos.x - lastPoint.x;
        int dy = mousePos.y - lastPoint.y;

        float newX = adapter.getSelectedObject().getScale().x + dx;
        float newY = adapter.getSelectedObject().getScale().y - dy;

        adapter.getSelectedObject().setScale(newX, newY);
    }

    protected void rotateObject(Point mousePos) {
        int dy = mousePos.y - lastPoint.y;
        float newY = adapter.getSelectedObject().getRotation() + dy * 0.5f;
        adapter.getSelectedObject().setRotation(newY);
    }

    protected boolean checkPointInRect(int w, int h, Point center, Point posi, float rot) {
        double rotRad = Math.PI * rot / 180.0f;
        float tx = posi.x - center.x;
        float ty = posi.y - center.y;
        double dx = tx * Math.cos(rotRad) - ty * Math.sin(rotRad);
        double dy = tx * Math.sin(rotRad) - ty * Math.cos(rotRad);

        dx += center.x;
        dy += center.y;

        return (dx > center.x - w/2 && dx < center.x + w/2 && dy > center.y - h/2 && dy < center.y + h/2);
    }

    public float getCamXOffset() {
        return camXOffset;
    }

    public float getCamYOffset() {
        return camYOffset;
    }

    public GameObject getSelectedObject() {
        return adapter.getSelectedObject();
    }

    public void setSelectedObject(GameObject go) {
        this.adapter.setSelectedObject(go);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            lastPoint = e.getPoint();
            if (e.isControlDown()) {
                return;
            }

            List<GameObject> selectedObjects = getSelectedGameObjects(lastPoint.x, lastPoint.y);
            int size = selectedObjects.size();

            if (size > 0) {
                adapter.setSelectedObject(selectedObjects.get(size - 1));
                //TODO: finish this block
            } else {
                adapter.setSelectedObject(null);
                //TODO: finish this block;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        Point pos = e.getPoint();

        if (e.isControlDown()) {
            int dx = pos.x - lastPoint.x;
            int dy = pos.y - lastPoint.y;
            camXOffset += dx;
            camYOffset += dy;
        } else if (adapter.getSelectedObject() != null) {
            //TODO: finish this block
        }

        lastPoint = pos;
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
}