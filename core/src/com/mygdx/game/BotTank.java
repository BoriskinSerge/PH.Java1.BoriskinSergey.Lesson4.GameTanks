package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Xander on 18.03.2016.
 */
public class BotTank extends BaseTank {

    private enum BotState { // Перечисление возможных состояний бота
        IDLE, MOVE, ROTATE_RIGHT, ROTATE_LEFT
    }

    private static Texture myTexture;

    float currentTime;
    float nextTime;
    BotState action;

    public BotTank(Vector2 position) {
        super();
        currentTime = 0.0f;
        nextTime = 0.0f;
        action = BotState.IDLE;
        if (myTexture == null)
            myTexture = new Texture("TANK2.tga");
        this.position = position;
        this.angle = 270;
        enginePower = 0.1f;
        rotateSpeed = 4.0f;
        size = myTexture.getWidth();
        scale = 1.2f + (MainClass.rand.nextFloat() - 0.5f);
    }

    public void draw(SpriteBatch batch) { // Рисуем бота
        batch.draw(myTexture, position.x, position.y, size / 2, size / 2, size, size, scale, scale, angle, 0, 0, size, size, false, false);
    }

    @Override
    public void update() { // Обновляем логику бота
        super.update();
        switch (action) {
            case MOVE: // Если бот в состоянии движения, просто выполняем ускорение танка
                accelerate();
                break;
            case ROTATE_LEFT: // Поворот налево и ускорение
                rotate(0.2f);
                accelerate();
                break;
            case ROTATE_RIGHT: // Поворот направо и ускорение
                rotate(-0.2f);
                accelerate();
                break;
        }
        currentTime += Gdx.graphics.getDeltaTime();  // Считаем текущее время танка
        if(currentTime > nextTime) { // Если настала пора менять действие
            nextTime = 2.0f + MainClass.rand.nextFloat() * 3.0f; // Выбираем время до следующей смены действия
            currentTime = 0.0f; // Таймер обнуляем
            action = BotState.values()[MainClass.rand.nextInt(4)]; // Выбираем случайное действие для выполенния
        }
    }

}
