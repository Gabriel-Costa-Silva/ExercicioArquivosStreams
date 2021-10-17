package br.com.letscode;

import lombok.Value;

import java.util.List;

@Value
public class Actor {
     int index;
     int Year;
     int Age;
     String Name;
     String Movie;


    public static Actor construtorPorLinha(String line) {
        String[] split = line.split(";");
        return new Actor(
                Integer.parseInt(split[0].replace(" ","")),
                Integer.parseInt(split[1].replace(" ","")),
                Integer.parseInt(split[2].replace(" ","")),
                split[3].trim(),
                split[4].trim());

    }
    public boolean verificaRepeticao(Actor actor, List<Actor> listaAtores) {
        int contadorOscar = 0;
        for(Actor ator : listaAtores) {
            if (actor.getName().equals(ator.getName())) {
                contadorOscar++;
            }
                if (contadorOscar > 1) {
                    return true;
                }
        }
        return false;
    }
}
