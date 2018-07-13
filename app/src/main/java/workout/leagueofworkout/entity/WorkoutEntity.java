package workout.leagueofworkout.entity;

import android.provider.Telephony;

import java.io.Serializable;

public class WorkoutEntity implements Serializable {
    private Squat squat;
    private Pompes pompes;
    private Gainage gainage;
    private Abdos abdos;
    private Biceps biceps;
    private Epaules epaules;


    public WorkoutEntity(ParticipantEntity participantEntity, GameEntity gameEntity) {
        double gameDuration = gameEntity.getGameDuration()/60;
        boolean isWin = participantEntity.isWin();
        double ratio = getRatio(participantEntity),
                killParticipation = getKillParticipation(gameEntity, participantEntity);

        this.squat = new Squat(gameDuration, ratio, killParticipation, isWin);
        this.pompes = new Pompes(gameDuration, ratio, killParticipation, isWin);
        this.gainage = new Gainage(gameDuration, ratio, killParticipation, isWin);
        this.abdos = new Abdos(gameDuration, ratio, killParticipation, isWin);
        this.biceps = new Biceps(gameDuration, ratio, killParticipation, isWin);
        this.epaules = new Epaules(gameDuration, ratio, killParticipation, isWin);
    }


    //Calcul du ratio kill+assist/death
    //Renvoie -1 si aucune mort : KDA parfait
    public double getRatio(ParticipantEntity participantEntity){
        if(participantEntity.getDeaths()!=0){
            return ((double) participantEntity.getKills() +
                    (double) participantEntity.getAssists()) /
                    (double) participantEntity.getDeaths();
        } else{
            return -1;
        }
    }

    //Calcul du ratio kill participation du joueur;
    //Renvoie 0 si la team n'a fait aucun kill
    public double getKillParticipation(GameEntity gameEntity, ParticipantEntity participantEntity){
        int totalTeamKills = 0;
        if(participantEntity.getParticipantId()<6){
            for(int i=0; i<5; i++){
                totalTeamKills += gameEntity.getParticipantList().get(i).getKills();
            }
        } else {
            for(int i=5; i<10; i++){
                totalTeamKills += gameEntity.getParticipantList().get(i).getKills();
            }
        }
        if (totalTeamKills!=0){
            return ((double) participantEntity.getKills() +
                    (double) participantEntity.getAssists()) /
                    (double) totalTeamKills;
        } else {
            return 0;
        }
    }

    public Squat getSquat() {
        return squat;
    }

    public Pompes getPompes() {
        return pompes;
    }

    public Gainage getGainage() {
        return gainage;
    }

    public Abdos getAbdos() {
        return abdos;
    }

    public Biceps getBiceps() {
        return biceps;
    }

    public Epaules getEpaules() {
        return epaules;
    }

    public class Squat implements Serializable{
        private long number;
        private int series;

        public Squat(double duration, double ratio, double killParticipation, boolean win) {
            this.series = calcSeries(duration, win);
            this.number = calcNumber(ratio, killParticipation);
        }

        //Calcul du nombre de séries
        //Si inférieur à 20 minutes : 1 série
        //Si entre 20 et 30 minutes : 2 séries
        //Sinon 3 séries
        //On ajoute une série si la game est perdue
        public int calcSeries(double duration, boolean win){
            int series;
            if(duration<20){
                series = 1;
            } else if(duration<30){
                series = 2;
            } else {
                series = 3;
            }
            if(!win){
                series+=1;
            }
            return series;
        }


        //Calcul des répetitions par séries
        //Si sur moins de 30% des kills : 30 répétitions
        //etc
        public long calcNumber(double ratio, double killParticipation){
        //TODO :   SHARED REFERENCES A INTEGRER
            if(killParticipation<0.3){
                return  30;
            } else if (killParticipation<0.5){
                return  25;
            } else if (killParticipation<0.7){
                return  20;
            }else {
                return 15;
            }
        }

        public int getSeries() {
            return series;
        }

        public long getNumber() {
            return number;
        }
    }

    public class Pompes implements Serializable{
        private long number;
        private int series;

        public Pompes(double duration, double ratio, double killParticipation, boolean win) {
            this.series = calcSeries(duration, win);
            this.number = calcNumber(ratio, killParticipation);
        }

        //Calcul du nombre de séries
        //Si inférieur à 20 minutes : 1 série
        //Si entre 20 et 30 minutes : 2 séries
        //Sinon 3 séries
        //On ajoute une série si la game est perdue
        public int calcSeries(double duration, boolean win){
            int series;
            if(duration<20){
                series = 1;
            } else if(duration<30){
                series = 2;
            } else {
                series = 3;
            }
            if(!win){
                series+=1;
            }
            return series;
        }


        //Calcul des répetitions par séries
        //Si sur moins de 30% des kills : 30 répétitions
        //etc
        public long calcNumber(double ratio, double killParticipation){
            //TODO :   SHARED REFERENCES A INTEGRER
            if(killParticipation<0.3){
                return  15;
            } else if (killParticipation<0.5){
                return  13;
            } else if (killParticipation<0.7){
                return  11;
            }else {
                return 9;
            }
        }

        public int getSeries() {
            return series;
        }

        public long getNumber() {
            return number;
        }
    }

    public class Gainage implements Serializable{
        private long number;
        private int series;

        public Gainage(double duration, double ratio, double killParticipation, boolean win) {
            this.series = calcSeries(duration, win);
            this.number = calcNumber(ratio, killParticipation);
        }

        //Calcul du nombre de séries
        //Si inférieur à 20 minutes : 1 série
        //Si entre 20 et 30 minutes : 2 séries
        //Sinon 3 séries
        //On ajoute une série si la game est perdue
        public int calcSeries(double duration, boolean win){
            int series;
            if(duration<20){
                series = 1;
            } else if(duration<30){
                series = 2;
            } else {
                series = 3;
            }
            if(!win){
                series+=1;
            }
            return series;
        }


        //Calcul des répetitions par séries
        //Si sur moins de 30% des kills : 30 répétitions
        //etc
        public long calcNumber(double ratio, double killParticipation){
            //TODO :   SHARED REFERENCES A INTEGRER
            if(killParticipation<0.3){
                return  45;
            } else if (killParticipation<0.5){
                return  40;
            } else if (killParticipation<0.7){
                return  35;
            }else {
                return 30;
            }
        }

        public int getSeries() {
            return series;
        }

        public long getNumber() {
            return number;
        }
    }

    public class Abdos implements Serializable{
        private long number;
        private int series;

        public Abdos(double duration, double ratio, double killParticipation, boolean win) {
            this.series = calcSeries(duration, win);
            this.number = calcNumber(ratio, killParticipation);
        }

        //Calcul du nombre de séries
        //Si inférieur à 20 minutes : 1 série
        //Si entre 20 et 30 minutes : 2 séries
        //Sinon 3 séries
        //On ajoute une série si la game est perdue
        public int calcSeries(double duration, boolean win){
            int series;
            if(duration<20){
                series = 1;
            } else if(duration<30){
                series = 2;
            } else {
                series = 3;
            }
            if(!win){
                series+=1;
            }
            return series;
        }


        //Calcul des répetitions par séries
        //Si sur moins de 30% des kills : 30 répétitions
        //etc
        public long calcNumber(double ratio, double killParticipation){
            //TODO :   SHARED REFERENCES A INTEGRER
            if(killParticipation<0.3){
                return  25;
            } else if (killParticipation<0.5){
                return  20;
            } else if (killParticipation<0.7){
                return  15;
            }else {
                return 10;
            }
        }

        public int getSeries() {
            return series;
        }

        public long getNumber() {
            return number;
        }
    }

    public class Biceps implements Serializable{
        private long number;
        private int series;

        public Biceps(double duration, double ratio, double killParticipation, boolean win) {
            this.series = calcSeries(duration, win);
            this.number = calcNumber(ratio, killParticipation);
        }

        //Calcul du nombre de séries
        //Si inférieur à 20 minutes : 1 série
        //Si entre 20 et 30 minutes : 2 séries
        //Sinon 3 séries
        //On ajoute une série si la game est perdue
        public int calcSeries(double duration, boolean win){
            int series;
            if(duration<20){
                series = 1;
            } else if(duration<30){
                series = 2;
            } else {
                series = 3;
            }
            if(!win){
                series+=1;
            }
            return series;
        }


        //Calcul des répetitions par séries
        //Si sur moins de 30% des kills : 30 répétitions
        //etc
        public long calcNumber(double ratio, double killParticipation){
            //TODO :   SHARED REFERENCES A INTEGRER
            if(killParticipation<0.3){
                return  25;
            } else if (killParticipation<0.5){
                return  20;
            } else if (killParticipation<0.7){
                return  15;
            }else {
                return 10;
            }
        }

        public int getSeries() {
            return series;
        }

        public long getNumber() {
            return number;
        }
    }

    public class Epaules implements Serializable{
        private long number;
        private int series;

        public Epaules(double duration, double ratio, double killParticipation, boolean win) {
            this.series = calcSeries(duration, win);
            this.number = calcNumber(ratio, killParticipation);
        }

        //Calcul du nombre de séries
        //Si inférieur à 20 minutes : 1 série
        //Si entre 20 et 30 minutes : 2 séries
        //Sinon 3 séries
        //On ajoute une série si la game est perdue
        public int calcSeries(double duration, boolean win){
            int series;
            if(duration<20){
                series = 1;
            } else if(duration<30){
                series = 2;
            } else {
                series = 3;
            }
            if(!win){
                series+=1;
            }
            return series;
        }


        //Calcul des répetitions par séries
        //Si sur moins de 30% des kills : 30 répétitions
        //etc
        public long calcNumber(double ratio, double killParticipation){
            //TODO :   SHARED REFERENCES A INTEGRER
            if(killParticipation<0.3){
                return  30;
            } else if (killParticipation<0.5){
                return  25;
            } else if (killParticipation<0.7){
                return  20;
            }else {
                return 15;
            }
        }

        public int getSeries() {
            return series;
        }

        public long getNumber() {
            return number;
        }
    }
}
