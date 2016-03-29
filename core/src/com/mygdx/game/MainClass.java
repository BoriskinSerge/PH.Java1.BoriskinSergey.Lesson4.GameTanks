package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.sun.xml.internal.xsom.impl.WildcardImpl;

import java.util.ArrayList;
import java.util.Random;

public class MainClass extends ApplicationAdapter {
    SpriteBatch batch; // Объявляем область отрисовки
    final int TANKS_COUNT = 10; // Кол-во танков
    BaseTank[] tanks = new BaseTank[TANKS_COUNT]; // Массив танков
    public static Random rand = new Random(); // Генератор случ чисел
    private Texture texGrass;
    public static ArrayList<Ammo> ammoList = new ArrayList<Ammo>();

    @Override
    public void create() { // Метод инициализации, срабатывает при запуске программы
        batch = new SpriteBatch(); // Создаем объект спрайтБатч
        tanks[0] = new PlayerTank(new Vector2(rand.nextInt(800), rand.nextInt(600))); // Создаем игрока
        texGrass = new Texture("Grass.png");
        for (int i = 1; i < tanks.length; i++) { // Создаем ботов
            tanks[i] = new BotTank(new Vector2(rand.nextInt(800), rand.nextInt(600)));
        }
    }

    @Override
    public void render() { // Мето отрисовки, логику сюда писать не надо!
        update(); // Обновляем логику игры
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // Задаем цвет очистки экрана
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Очистка экрана
        batch.begin(); // Начинаем отрисовку

        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 19; j++) {
                batch.draw(texGrass, i * 32, j * 32); // Заливаем задний фон текстурой травы поблочно
            }
        }

        for (int i = 0; i < tanks.length; i++) { // Рисуем все танки
            if (tanks[i].isAlive()) // Рисуем танк только если он жив
                tanks[i].draw(batch);
        }

        for (int i = 0; i < ammoList.size(); i++) {
            if (ammoList.get(i).isAlive())
                ammoList.get(i).draw(batch);
        }

        batch.end(); // Заканчиваем и выводим на экран
    }

    public void update() { // Обновляем игровую логику
        for (int i = 0; i < tanks.length; i++) { // Обновляем логику всех танков
            if (tanks[i].isAlive()) // только если танк жив
                tanks[i].update();
        }
        for (int i = 0; i < ammoList.size(); i++) {
            if (ammoList.get(i).isAlive())
                ammoList.get(i).update();
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (tanks[0].isLoaded())
                tanks[0].doShot();
        }

        //Проверка столкновений танков
        for (int i = 0; i < tanks.length - 1; i++) { // Бежим по массиву танков
            {
                if (tanks[i].isAlive()) { // Если i-й танк жив начинаем сравнивать ег со всеми остальными
                    for (int j = i + 1; j < tanks.length; j++) { // бежим по возможным танкам
                        if (tanks[j].isAlive()) { // если второй танк тоже жив
                            Vector2 vlen = tanks[i].getPosition().cpy().sub(tanks[j].getPosition()); // находим вектор асстояния между ними
                            float flen = vlen.len(); // считаем пасстояние(длину вектора)
                            if (flen < 32) { // если расстояние между танками меньше 32 пикселей
                                tanks[i].damage(1); // повреждаем танк
                                tanks[j].damage(1); // повреждаем танк
                                tanks[i].impulse(vlen.cpy().nor().scl(4.0f)); // придаем танку импуль, и чуть откидываем
                                tanks[j].impulse(vlen.cpy().nor().scl(-4.0f)); // придаем танку импуль, и чуть откидываем
                            }
                        }
                    }
                }
            }
        }
        //Проверка попаданий снарядов

        for (int i = 0; i < ammoList.size(); i++) {
            if (ammoList.get(i).isAlive()) {
                for (int j = 0; j < tanks.length; j++) {
                    if ((tanks[j].isAlive())&&(!ammoList.get(i).getOwner().equals(tanks[j])))
                            if (tanks[j].getPosition().cpy().dst(ammoList.get(i).getPosition()) < (tanks[j].size * tanks[j].scale + ammoList.get(i).getSize()) / 2) {
                                tanks[j].destroy();
                                ammoList.get(i).destroy();
                            }

                }
            }
        }
    }
}
