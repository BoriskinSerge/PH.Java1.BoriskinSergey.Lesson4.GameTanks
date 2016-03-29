package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Xander on 18.03.2016.
 */
public class PlayerTank extends BaseTank {

    private static Texture myTexture;
    private Weapon tankWeapon;

    public PlayerTank(Vector2 position) {
        super();
        hp = 100;
        myTexture = new Texture("base.tga");
        this.position = position;
        this.angle = 0;
        rotateSpeed = 2.0f;
        enginePower = 0.1f;
        size = myTexture.getWidth();
        scale = 1.0f;
        tankWeapon=new Weapon(this.position.cpy());
    }

    public void draw(SpriteBatch batch) {
        batch.draw(myTexture, position.x, position.y, size / 2, size / 2, size, size, scale, scale, angle, 0, 0, size, size, false, false);
        tankWeapon.draw(batch);

    }

    @Override
    public void doShot() {
        super.doShot();
        for (int i = 0; i < MainClass.ammoList.size(); i++) {
            if (!MainClass.ammoList.get(i).isAlive()) {
                loaded = false;
                MainClass.ammoList.get(i).setAmmo(this, this.getTankWeapon().getAngle());
                break;
            }
        }
        if (loaded) {
            loaded = false;
            MainClass.ammoList.add(new Ammo(this, this.getTankWeapon().getAngle()));
        }
        cooldownCurrentTime = 0.0f;
        cooldownNextTime = cooldown;
    }

    public Weapon getTankWeapon() {
        return tankWeapon;
    }

    @Override
    public void update() {
        super.update();
        tankWeapon.setPosition(this.getPosition().cpy());
        tankWeapon.update();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            rotate(1.0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            rotate(-1.0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            accelerate();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            brake();
        }
    }

}
