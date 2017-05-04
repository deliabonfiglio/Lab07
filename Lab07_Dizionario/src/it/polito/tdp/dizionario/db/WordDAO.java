package it.polito.tdp.dizionario.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class WordDAO {

	/*
	 * 
	 * Ritorna tutte le parole di una data lunghezza che differiscono per un solo carattere
	 */
	public List<String> getAllSimilarWords(String parola, int numeroLettere) {

		Connection conn = DBConnect.getInstance().getConnection();
		String sql = "SELECT nome FROM parola WHERE LENGTH(nome) = ? AND nome LIKE ?";
		PreparedStatement st;
		List<String> parole = new ArrayList<String>();

		try {

			st = conn.prepareStatement(sql);
			st.setInt(1, numeroLettere);

			for (int i = 0; i < numeroLettere; i++) {
				char[] word = parola.toCharArray();
				word[i] = '_';
				String built = "";
				for (int j = 0; j < numeroLettere; j++) {
					built += Character.toString(word[j]);
				}

				st.setString(2, built);
				ResultSet res = st.executeQuery();

				while (res.next()) {
					if (!res.getString("nome").equals(parola))
						parole.add(res.getString("nome"));
				}

			}
			conn.close();
			return parole;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	/*
	 * Ritorna tutte le parole di una data lunghezza
	 */
	public List<String> getAllWordsFixedLength(int numeroLettere) {

		Connection conn = DBConnect.getInstance().getConnection();
		String sql = "SELECT nome FROM parola WHERE LENGTH(nome) = ?;";
		PreparedStatement st;

		try {

			st = conn.prepareStatement(sql);
			st.setInt(1, numeroLettere);
			ResultSet res = st.executeQuery();
			System.out.println(sql.toString());
			List<String> parole = new ArrayList<String>();

			while (res.next())
				parole.add(res.getString("nome"));

			return parole;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

}
