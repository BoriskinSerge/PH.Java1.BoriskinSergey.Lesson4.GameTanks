package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Sergey on 28.03.16.
 */
public class Ammo {
    private Vector2 position; // Положение
    private Vector2 velocity; // Скорость
    private float angle; // Угол поворота текущий
    private boolean life; // Жив ли танк или нет
    private float initVelocity = 20.0f; // начальная скорость снаряда
    private BaseTank owner;

    private static Texture myTexture = new Texture("Ammo.tga");;

    private int size=myTexture.getWidth();;

    public Ammo(BaseTank owner, float angle) {
        this.position = owner.getPosition().cpy();
        this.angle = angle;
        this.velocity = new Vector2(1.0f, 0.0f);
        this.velocity.rotate(angle);
        this.velocity.scl(initVelocity);
        life = true;
        this.owner=owner;
    }

    public void setAmmo(BaseTank owner, float angle) {
        this.position = owner.getPosition().cpy();
        this.angle = angle;
        this.velocity = new Vector2(1.0f, 0.0f);
        this.velocity.rotate(angle);
        this.velocity.scl(initVelocity);
        life = true;
        this.owner = owner;
    }

    public int getSize() {
        return size;
    }

    public Vector2 getPosition() {
        return position;
    }

    public BaseTank getOwner() {
        return owner;
    }

    public void draw(SpriteBatch batch) { // Рисуем снаряд
        batch.draw(myTexture, position.x, position.y, size / 2, size / 2, size, size, 1.0f, 1.0f, angle, 0, 0, size, size, false, false);
    }

    public void destroy() {
        life = false;
    } // Уничтожаем снаряд

    public boolean isAlive() {
        return life;
    }

    public void update() {
        position.add(velocity); // Все время прибавляем к положению  скорость
        velocity.scl(0.96f); // Уменьшаем скорость на 4%
        if (velocity.len()<1.0f) destroy();
        if ((position.x > Gdx.graphics.getWidth())||(position.x < -myTexture.getWidth())||(position.y > Gdx.graphics.getHeight()) || (position.y < -myTexture.getWidth())) destroy();
    }

}
