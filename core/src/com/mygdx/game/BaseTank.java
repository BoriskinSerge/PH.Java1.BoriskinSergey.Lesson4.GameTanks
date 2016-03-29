package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Xander on 18.03.2016.
 */
public abstract class BaseTank {
    protected Vector2 position; // Положение
    protected Vector2 velocity; // Скорость
    protected Vector2 acceleration; // Ускорение
    protected float enginePower; // Мощность двигателя, насколько быстро танк ускоряется
    protected float angle; // Угол поворота текущий
    protected float rotateSpeed; // Скорость поворота
    protected int size; // Размер танка в пикселях
    protected float scale; // Масштаб танка
    protected boolean life; // Жив ли танк или нет
    protected int hp; // Здоровье танка
    protected float cooldown; // Время перезарядки
    protected boolean loaded; //орудие заряжено
    protected float cooldownCurrentTime;
    protected float cooldownNextTime;



    public float getAngle() {
        return angle;
    }

    public void doShot() {


    }

    public boolean isLoaded() {
        return loaded;
    }

    public boolean isAlive() {
        return life;
    } // Проверка жи ли танк

    public Vector2 getPosition() {
        return position;
    } // Узнаем положение танка

    public void impulse(Vector2 v) { // На занятии этот метод назывался setSpeed(), метод для отталкивания танков друг от друга
        velocity = v; // Вручную указываем новую скорость танка
        position.add(v); // и сразу чуть откидываем его по направлению новой скорости
    }


    public void damage(int dmg) { // Урон танку, отнять здоровье и сделать проверку жизни
        hp -= dmg; // Из здоровья танка вычитается урон
        if (hp <= 0) { // Если здоровье меньше 0
            destroy(); // уничтожаем танк

        }
    }

    public void destroy() {
        life = false;
    } // Уничтожаем танк

    public BaseTank() { // Конструктор зхаполняющий базовую часть
        life = true;
        velocity = new Vector2(0.0f, 0.0f);
        acceleration = new Vector2(0.0f, 0.0f);
        hp = 10;
        loaded = true;
        cooldown = 0.5f;

    }

    public void rotate(float a) { // Поворачиваем танк
        angle += a * rotateSpeed;
//        position.rotate(position.cpy().angle()+a * rotateSpeed);
    }

    public void accelerate() { // Ускорение танка
        acceleration = new Vector2(1.0f, 0.0f);
        acceleration.setAngle(angle);
        acceleration.scl(enginePower);
        velocity.add(acceleration); // К вектору скорости прибавляем вектор ускорения

    }

    public void brake() {
        acceleration = new Vector2(1.0f, 0.0f);
        acceleration.rotate(angle + 180);
        acceleration.scl(enginePower / 4.0f);
        velocity.add(acceleration); // К вектору скорости прибавляем вектор ускорения направленный назад
    }

    public abstract void draw(SpriteBatch batch);

    public void update() {
        position.add(velocity); // Все время прибавляем к положению танка скорость
        velocity.scl(0.96f); // Уменьшаем скорость на 4%

        if (position.x > Gdx.graphics.getWidth()) position.x = -size; // Проверка выхода танка за экран
        if (position.x < -size) position.x = Gdx.graphics.getWidth(); // Проверка выхода танка за экран
        if (position.y > Gdx.graphics.getHeight()) position.y = -size; // Проверка выхода танка за экран
        if (position.y < -size) position.y = Gdx.graphics.getHeight(); // Проверка выхода танка за экран
        if (!loaded) {
            cooldownCurrentTime += Gdx.graphics.getDeltaTime();
            if (cooldownCurrentTime > cooldownNextTime) {
                loaded = true;
            }

        }
    }
}
