package br.com.letscode;

import lombok.Value;
@Value
public class Conquistas {

     String filme;
     int ano;
     int idade;

    public String toString(){
        return "com o filme "+filme+" no ano de "+ano+ " com a idade de "+idade + " anos \ne";
    }
}
