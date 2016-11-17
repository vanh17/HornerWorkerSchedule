package com.example.enviro.completebitsandpizzas;

/**
 * Created by Enviro on 11/14/2016.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Shift {
        private Date date ;
        private int originalWorker;
        private int currentWorker;

        public static final Shift[] shifts = {
                new Shift(new Date(116, 10, 10, 0, 0), 0, 0),
                new Shift(new Date(116, 10, 10, 7, 0), 0, 0),
                new Shift(new Date(116, 10, 11, 12, 30), 0, 0),
                new Shift(new Date(116, 10, 12, 17, 30), 0, 0),
                new Shift(new Date(116, 10, 16, 20, 30), 0, 0),
                new Shift(new Date(116, 10, 17, 22, 45), 0, 0),
                new Shift(new Date(116, 10, 18, 12, 15), 0, 0),
                new Shift(new Date(116, 10, 19, 14, 35), 0, 0),
                new Shift(new Date(116, 10, 20, 15, 55), 0, 0),
                new Shift(new Date(116, 10, 21, 17, 30), 0, -1),
                new Shift(new Date(116, 10, 22, 5, 45), 0, -1),
                new Shift(new Date(116, 10, 23, 7, 0), 0, -1),
                new Shift(new Date(116, 10, 24, 12, 30), 0, -1),
                new Shift(new Date(116, 10, 25, 17, 30), 0, 0),
                new Shift(new Date(116, 10, 26, 20, 30), 0, 0),
                new Shift(new Date(116, 10, 27, 22, 45), 1, 0),
                new Shift(new Date(116, 10, 28, 12, 15), 2, 0),
                new Shift(new Date(116, 10, 29, 14, 35), 3, 0),
                new Shift(new Date(116, 10, 30, 15, 55), 4, 0),
                new Shift(new Date(116, 11, 1, 17, 30), 5, 0),
                new Shift(new Date(116, 10, 28, 22, 45), 1, -1),
                new Shift(new Date(116, 10, 29, 12, 15), 2, 0),
                new Shift(new Date(116, 10, 30, 14, 35), 3, -1),
                new Shift(new Date(116, 11, 1, 15, 55), 4, -1),
                new Shift(new Date(116, 11, 2, 17, 30), 5, -1),
        };

        private Shift(Date date, int originalWorker, int currentWorker) {
            this.date = date;
            this.originalWorker = originalWorker;
            this.currentWorker = currentWorker;
        }


        //Getters
        public Date getDate() {
            return date;
        }

        public int getOriginalWorker() {
            return originalWorker;
        }

        public int getCurrentWorker() {
            return currentWorker;
        }

        //ToString methods
        public String dateToString() {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            String dateS = df.format(this.date);
            return dateS;
        }

        public String toString() {
            String orgW = "orig: " + Integer.toString(this.originalWorker);
            String curW = "cur: " + Integer.toString(this.currentWorker);
            return dateToString() + " " + orgW + " " + curW;
        }

        //Setters
        public void setOriginalWorker(int originalWorker) {
            this.originalWorker = originalWorker;
        }

        public void setCurrentWorker(int currentWorker) {

            this.currentWorker = currentWorker;
        }
}
