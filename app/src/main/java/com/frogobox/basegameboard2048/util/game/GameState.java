package com.frogobox.basegameboard2048.util.game;

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * BaseGameBoard2048
 * Copyright (C) 02/01/2020.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.basegameboard2048.model
 *
 */

import com.frogobox.basegameboard2048.model.Element;

import java.io.Serializable;

public class GameState implements Serializable {
    public int n = 4;
    public int[] numbers;
    public int[] last_numbers;
    public int points = 0;
    public int last_points = 0;
    public boolean undo = false;

    public GameState(int size) {
        numbers = new int[size * size];
    }

    public GameState(int[][] e) {
        int length = 1;
        for (int i = 0; i < e.length; i++) {
            if (e[i].length > length)
                length = e[i].length;
        }
        this.n = e.length;
        numbers = new int[e.length * e.length];
        int c = 0;
        for (int i = 0; i < e.length; i++) {
            for (int j = 0; j < e[i].length; j++) {
                numbers[c++] = e[i][j];
            }
        }
        last_numbers = numbers;
    }

    public GameState(Element[][] e, Element[][] e2) {
        int length = 1;
        for (int i = 0; i < e.length; i++) {
            if (e[i].length > length)
                length = e[i].length;
        }
        this.n = e.length;
        numbers = new int[e.length * e.length];
        int c = 0;
        for (int i = 0; i < e.length; i++) {
            for (int j = 0; j < e[i].length; j++) {

                numbers[c++] = e[i][j].number;
            }
        }
        length = 1;
        for (int i = 0; i < e2.length; i++) {
            if (e2[i].length > length)
                length = e2[i].length;
        }
        last_numbers = new int[e2.length * e2.length];
        c = 0;
        for (int i = 0; i < e2.length; i++) {
            for (int j = 0; j < e2[i].length; j++) {

                last_numbers[c++] = e2[i][j].number;
            }
        }
    }

    public int getNumber(int i, int j) {

        try {
            return numbers[i * n + j];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getLastNumber(int i, int j) {

        try {
            return last_numbers[i * n + j];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return 0;
    }


    @Override
    public String toString() {
        String result = "numbers: ";
        for (int i : numbers) {
            result += i + " ";
        }
        result += ", n: " + n;
        result += ", points: " + points;
        result += ", undo: " + undo;
        return result;
    }
}
