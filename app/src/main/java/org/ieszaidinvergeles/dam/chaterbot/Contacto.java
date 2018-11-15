package org.ieszaidinvergeles.dam.chaterbot;


    public class Contacto {

        private long id;
        private String nombre;
        private String message;


        public Contacto(){
            this(0,"","");
        }
        public Contacto(String nombre,String message){
            this(0,nombre,message);
        }

        public Contacto(long id,String nombre,String message) {
            this.id = id;
            this.nombre = nombre;
            this.message = message;

        }

        public long getId() {
            return id;
        }

        public Contacto setId(long id) {
            this.id = id;
            return this;
        }

        public String getNombre() {
            return nombre;
        }

        public Contacto setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }





    }
