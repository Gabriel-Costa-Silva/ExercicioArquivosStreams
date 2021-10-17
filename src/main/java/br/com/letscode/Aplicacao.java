package br.com.letscode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aplicacao {
    static List<Actor> maleActors;
    static List<Actor> femaleActors;

    public static void main(String[] args) {
        Aplicacao app = new Aplicacao();


        try {
            app.registroAtores();
            System.out.println(maleActors);
            System.out.println(femaleActors);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        questao1();
        questao2(femaleActors);
        questao3();
        questao4();
        questao5();
    }



    private static void questao1() {
        int idade = maleActors.get(0).getAge();
        int index = 0;
        for (Actor actor : maleActors) {
            if (idade > actor.getAge()) {
                idade = actor.getAge();
                index = actor.getIndex();
            }
        }
        System.out.printf("O ator(masculino) mais novo a ganhar um oscar é%s, com %d anos.\n",
                maleActors.get(index - 1).getName(),
                idade);
    }

    private static void questao2(List<Actor> listaGanhadoras) {
        int maiorOcorrencia = 0;
        StringBuilder ganhadora = new StringBuilder(" ");
        for (Actor mulher : listaGanhadoras) {
            int ocorrencia = 0;
            for (Actor mulherComparavel : listaGanhadoras) {
                if (mulher.getName().equals(mulherComparavel.getName())) {
                    ocorrencia++;
                }
            }
            if (ocorrencia > maiorOcorrencia) {
                maiorOcorrencia = ocorrencia;
                ganhadora = new StringBuilder(mulher.getName() + " ");
            }
            if (ocorrencia == maiorOcorrencia && 0 != listaGanhadoras.indexOf(mulher) && !ganhadora.toString().contains(mulher.getName())) {
                ganhadora.append(mulher.getName()).append(" ");
            }
        }
        System.out.printf("A maior ganhadora do oscar foi %s, com %d premiações\n", ganhadora, maiorOcorrencia);
    }

    private static void questao3() throws IndexOutOfBoundsException {
        System.out.print("Entre 20 e 30 anos, ");
        try (Stream<Actor> AtrizesRevelacao = femaleActors.stream().filter(Actor -> Actor.getAge() <= 30)) {
            questao2(AtrizesRevelacao.toList());
        }


    }

    private static void questao4() {
        List<String> vencedoresOscar = new ArrayList<>();
        vencedoresOscar = maleActors.stream()
                .filter(Actor-> Actor.verificaRepeticao(Actor,maleActors))
                .map(Actor::getName)
                .distinct()
                .collect(Collectors.toList()); // <- Diferente do método "toList()", este método permite a mutabilidade da lista
        List<String> vencedorasOscar = new ArrayList<>();
        vencedorasOscar = femaleActors.stream()
                .filter(Actor-> Actor.verificaRepeticao(Actor,femaleActors))
                .map(Actor::getName)
                .distinct()
                .toList();
        for(String atrizes : vencedorasOscar){
            vencedoresOscar.add(atrizes);
        }
        System.out.println("Os atores e atrizes que conquistaram mais de um Oscar foram : ");
        vencedoresOscar.forEach(System.out::println);
    }
    private static void questao5() {

        System.out.println("Digite o nome do ator que você deseja buscar : ");
        Scanner teclado = new Scanner(System.in);
        String nomeAtor = teclado.nextLine();
        System.out.printf("O ator(a) %s, conquistou :",nomeAtor);
        List<Actor> atorProcurado;
        atorProcurado = maleActors.stream()
                .filter(Actor ->Actor.getName().equals(nomeAtor))
                .collect(Collectors.toList());
        atorProcurado.addAll(femaleActors.stream()
                .filter(Actor -> Actor.getName().equals(nomeAtor))
                .collect(Collectors.toList()));
        int atorConquistas = (int) atorProcurado.stream().count();
        List<Conquistas> conquistas = new ArrayList<>();
        for(Actor ator: atorProcurado){
            conquistas.add(new Conquistas(ator.getMovie(), ator.getYear(),ator.getAge()));
        }
        StringBuilder textoConquistas = new StringBuilder();
        for(Conquistas conquistaOscar : conquistas){
            textoConquistas.append(conquistaOscar.toString());
        }
        System.out.printf("%d Oscar(s) %s",atorConquistas, textoConquistas.deleteCharAt(textoConquistas.lastIndexOf("e")));

    }



    private void registroAtores() throws URISyntaxException {
        try (Stream<String> streamActors = Files.lines(Path.of("C:\\Users\\Gabriel\\ExercícioArquivosStreams\\src\\main\\resources\\MenWhoWonOscar.csv"))) {
            maleActors = streamActors
                    .skip(1)
                    .map(Actor::construtorPorLinha)
                    .toList();
        } catch (IOException ioException) {
            ioException.printStackTrace();

        }
        try (Stream<String> streamActors = Files.lines(Path.of("C:\\Users\\Gabriel\\ExercícioArquivosStreams\\src\\main\\resources\\WomenWhoWonOscar.csv"))) {
            femaleActors = streamActors
                    .skip(1)
                    .map(Actor::construtorPorLinha)
                    .toList();
        } catch (IOException ioException) {
            ioException.printStackTrace();

        }

    }


}
