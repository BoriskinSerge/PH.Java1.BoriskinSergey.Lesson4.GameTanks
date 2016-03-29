package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Sergey on 29.03.16.
 */
public class Weapon {
    private Vector2 position;
    private float angle;
    private Texture myTexture;

    public Weapon(Vector2 position) {
        this.position = position;
        angle = 0;
        myTexture = new Texture("weapon.tga");
    }

    public void draw(SpriteBatch batch) {

        batch.draw(myTexture, position.x, position.y, myTexture.getWidth()/2, myTexture.getHeight()/2,
                myTexture.getWidth(), myTexture.getHeight(), 1.0f, 1.0f, angle, 0, 0, myTexture.getWidth(),
                myTexture.getHeight(), false, false);
    }

    public void update() {
//        Vector2 direction = new Vector2(-Gdx.input.getX(), Gdx.input.getY());
//        angle=direction.angle();
        angle = (float) Math.toDegrees(Math.atan2(Gdx.graphics.getHeight() - position.y - myTexture.getWidth() / 2 - Gdx.input.getY() + 3, Gdx.input.getX() - myTexture.getHeight() / 2 - position.x + 3)) ;
        if (angle < 0) angle += 360;
    }


    public float getAngle() {
        return angle;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }


}
