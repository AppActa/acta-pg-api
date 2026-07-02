package br.com.acta.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Hash {
    private static final Argon2 ARGON2 = Argon2Factory.create();
    private static final int ITERACOES = 4;
    private static final int MEMORIA = 262144; // 256 megabytes
    private static final int PARALELISMO = 2;

    public static String gerarHash(String senha){
        return ARGON2.hash(ITERACOES, MEMORIA, PARALELISMO, senha.toCharArray());
    }

    public static boolean validarSenha(String hashBanco, String senhaEnviada){
        return ARGON2.verify(hashBanco, senhaEnviada.toCharArray());
    }
}
