package com.Trabalho_Paradigmas;

import com.Trabalho_Paradigmas.dto.GastoObraDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jpl7.Query;
import org.jpl7.Term;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.*;

@SpringBootApplication
public class TrabalhoParadigmasApplication {

	public static void main(String[] args) throws IOException {
		Scanner ler = new Scanner(System.in);

		System.out.println("Escolha o arquivo ou digite 0 caso não faça o upload dos dados:");
		String arquivoLeitura = ler.next();
		if(!arquivoLeitura.equals("0")){
			List<String> linhas = lerArquivo(arquivoLeitura);
			escreverArquivo(linhas);
		}
		while (true){
			System.out.println("Digite a consulta:");
			String consulta = ler.next();
			consultar(consulta);
		}
	}

	private static void escreverArquivo(List<String> linhas) throws IOException {
		FileWriter file = null;
		String arquivoEscrita = "base-de-dados.pl";
		file = new FileWriter(arquivoEscrita, true);

		BufferedWriter fw = new BufferedWriter(file);
		linhas.forEach(linha ->
		{
			try {
				fw.append("\n");
				fw.append(linha);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		fw.close();
	}
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

	private static void consultar(String consulta){

		String arquivoProlog = "base-de-dados.pl";
		Query q = new Query("consult('" + arquivoProlog + "')");
		q.hasSolution();
		q = new Query(consulta);

		List<String> result = new ArrayList<>();
		Map<String, Term>[] solutions = q.allSolutions();
		if(solutions.length == 0){
			result.clear();
			result.add("False");
		}
		for(int i = 0; i < solutions.length; i++) {

			if(!(solutions[i].isEmpty() || solutions.length == 0)) {
				result.add(solutions[i].toString());
			} else if (solutions[i].toString().contains("{}")) {
				result.clear();
				result.add("True");
			}
			else{
				result.add(solutions[i].toString());
			}
		}

		System.out.println(result);

	}
}
