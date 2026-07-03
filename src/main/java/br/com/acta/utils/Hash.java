package br.com.acta.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Hash {
    private static final Argon2 ARGON2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    private static final int ITERACOES = 4;
    private static final int MEMORIA = 262144; // 256 megabytes
    private static final int PARALELISMO = 2;
    private static final String PEPPER = System.getenv("PEPPER");

    private static char[] aplicarPepper(String senha){
        return (senha + PEPPER).toCharArray();
    }

    public static String gerarHash(String senha){
        return ARGON2.hash(ITERACOES, MEMORIA, PARALELISMO, aplicarPepper(senha));
    }

    public static boolean validarSenha(String hashBanco, String senhaEnviada){
        return ARGON2.verify(hashBanco, aplicarPepper(senhaEnviada));
    }
}
