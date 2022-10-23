package view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import excecao.ExplosaoException;
import excecao.SairException;
import model.Tabuleiro;

public class TabuleiroConsole {
	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;
			
			while(continuar) {
				cicloDoJogo();
				
				System.out.println("Outra partida? (S/N) ");
				String resposta = entrada.nextLine();
				
				if(resposta.equalsIgnoreCase("N")) {
					continuar = false;
				} else {
					tabuleiro.reiniciar();
				}
			}
			
		} catch (SairException e) {
			System.out.println("Tchau!");
		} finally {
			entrada.close();
		}
		
	}
	
	private void cicloDoJogo() {
		try {
			
			while(!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);
				
				String digitado = capturarValorDigitado("Digite (x, y): ");
				
				//trim tira os espacos em branco
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
										.map(e -> Integer.parseInt(e.trim()))
										.iterator();
				
				digitado = capturarValorDigitado("[1] Abrir OU [2] (Des)marcar ");
				
				if(digitado.equals("1")) {
					tabuleiro.abrir(xy.next(), xy.next());
				} else if(digitado.equals(digitado)) {
					tabuleiro.alternarMarcacao(xy.next(), xy.next());
				}
			}
			System.out.println(tabuleiro);
			System.out.println("Você ganhou!");
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Você perdeu!");
		}
	}
	
	private String capturarValorDigitado(String texto) {
		System.out.println(texto);
		String digitado = entrada.nextLine();
		
		if(digitado.equalsIgnoreCase("sair")) {
			throw new SairException();
		}
		
		return digitado;
	}
}
