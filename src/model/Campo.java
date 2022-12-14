package model;

import java.util.ArrayList;
import java.util.List;

import excecao.ExplosaoException;

public class Campo {
	private final int LINHA;
	private final int COLUNA;
	
	private boolean aberto;
	private boolean fechado;
	private boolean minado;
	private boolean marcado;
	
	private List<Campo> vizinhos = new ArrayList<Campo>();
	
	public Campo(int linha, int coluna){
		this.LINHA = linha;
		this.COLUNA = coluna;
	}
	
	public boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = LINHA != vizinho.LINHA;
		boolean colunaDiferente = COLUNA != vizinho.COLUNA;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(LINHA - vizinho.LINHA);
		int deltaColuna = Math.abs(COLUNA - vizinho.COLUNA);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if ((deltaGeral == 1 && !diagonal)
				||(deltaGeral == 2 && diagonal)) {
			
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
		
	}
	
	void alternarMarcacao() {
		if(!aberto) {
			marcado = !marcado;
		}
	}
	
	boolean abrir() {
		if (!aberto && !marcado) {
			aberto = true;
			
			if(minado) {
				throw new ExplosaoException();
			}
			
			if(vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		} else {
			return false;
		}
	}
	
	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	public boolean isMarcado() {
		return marcado;
	}

	public boolean isAberto() {
		return aberto;
	}

	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public int getLINHA() {
		return LINHA;
	}

	public int getCOLUNA() {
		return COLUNA;
	}

	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}

	public boolean isFechado() {
		return !isAberto();
	}

	public void setFechado(boolean fechado) {
		this.fechado = fechado;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && aberto;
		return desvendado || protegido;
	}
	
	long minasNaVizinhanca(){
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public boolean isMinado() {
		return minado;
	}

	public void setMinado(boolean minado) {
		this.minado = minado;
	}
	
	public String toString() {
		if(marcado) {
			return "x";
		} else if (aberto && minado) {
			return "*";
		} else if (aberto && minasNaVizinhanca() > 0) {
			return Long.toString(minasNaVizinhanca());
		} else if (aberto) {
			return " ";
		} else {
			return "?";
		}
	}
	
	void minar() {
		minado = true;
	}
	
	
}
