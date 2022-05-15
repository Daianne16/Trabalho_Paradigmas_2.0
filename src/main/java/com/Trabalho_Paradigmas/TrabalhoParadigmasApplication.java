package com.Trabalho_Paradigmas;

import com.Trabalho_Paradigmas.dto.GastoObraDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jpl7.PrologException;
import org.jpl7.Query;
import org.jpl7.Term;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.*;

@SpringBootApplication
public class TrabalhoParadigmasApplication {

	public static void main(String[] args) throws IOException {
		String arquivoEscrita = "base-de-dados.pl";
		Scanner ler = new Scanner(System.in);

		System.out.println("Escolha o arquivo ou digite 0 para pular o Upload dos dados:");
		String arquivoLeitura = ler.next();
		if(!arquivoLeitura.equals("0")){
			List<String> linhas = lerArquivo(arquivoLeitura);
			escreverArquivo(linhas, arquivoEscrita);
		}
		String consulta = "1";
		while (!consulta.equals("0")){
			System.out.println("Exemplos dos tipos de consulta:");
			System.out.println("1 - Produto X foi comprado: \n - compramos(‘tijolo’).");
			System.out.println("2 - Quantidade comprada de um produto: \n - quantidade('tijolo', Variavel)");
			System.out.println("3 - Valor total comprado de um produto: X \n - valor_total('tijolo',Variavel)");
			System.out.println("4 - O que foi comprado na data X: \n - comprado_em('13-05-2022',Variavel)");
			System.out.println("5 - Total comprado na loja X: \n - total_compras('loja',Variavel)");
			System.out.println("6 - Qual o produto mais comprado: \n - produto_mais_comprado(Variavel).");

			System.out.println();
			System.out.println("Digite a consulta baseado nos exemplos ou digite 0 para sair");
			consulta = ler.next();

			if (!consulta.equals("0")) {
				try {
					consultar(consulta, arquivoEscrita);
				} catch (PrologException e) {
					System.out.println("Consulta incorreta! Tente novamente. " + e.getMessage());
					System.out.println();
				}
			}
		}
	}

	// Escrita do arquivo baseado no site DevMedia : https://www.devmedia.com.br/leitura-e-escrita-de-arquivos-de-texto-em-java/25529
	private static void escreverArquivo(List<String> linhas, String arquivoEscrita) throws IOException {
		FileWriter file = null;
		file = new FileWriter(arquivoEscrita, true);

		BufferedWriter bw = new BufferedWriter(file);
		linhas.forEach(linha ->
		{
			try {
				bw.append("\n");
				bw.append(linha);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		bw.close();
	}

	//Leitura do arquivo XLS usando a biblioteca do Apache POI para leitura de arquivos
	private static List<String> lerArquivo(String caminho){
		List<String> linhas = new ArrayList<>();
		FileInputStream fileInputStream = null;
		GastoObraDTO gastoObraDTO = new GastoObraDTO();
		try {
			File file = new File(caminho);
			fileInputStream = new FileInputStream(file);

			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			Iterator<Row> iterator = xssfSheet.iterator();

			while(iterator.hasNext()){
				Row row = iterator.next();
				Iterator<Cell> cellIterator = row.iterator();
				String initial = "gasto(";
				while(cellIterator.hasNext()){
					Cell cell = cellIterator.next();
					if(cell.getCellType().equals(CellType.STRING)) {
						initial = initial.concat("'" + cell.getStringCellValue() + "',");
					} else if(cell.getCellType().equals(CellType.NUMERIC)) {
						initial = initial.concat(cell.getNumericCellValue() + ",");
					}
				}
				initial = initial.concat(")");
				initial = initial.replace(",)", ").");
				if(!initial.equals("gasto()")){
					linhas.add(initial);
				}
			}
			return linhas;
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	//Seguindo o exemplo do Youtube, para uso da biblioteca Jpl7
	private static void consultar(String consulta, String arquivoProlog){
		Query q = new Query("consult('" + arquivoProlog + "')");
		q.hasSolution();
		q = new Query(consulta);

		List<String> result = new ArrayList<>();
		Map<String, Term>[] solutions = q.allSolutions();
		for(int i = 0; i < solutions.length; i++) {
			if(!(solutions[i].isEmpty())) {
				result.add(solutions[i].toString());
			} else if (solutions[i].toString().contains("{}")) {
				result.clear();
				result.add("True");
			}
		}

		System.out.println(result);
		Scanner ler = new Scanner(System.in);
		System.out.println("Digite Enter para continuar");
		ler.nextLine();
	}
}
