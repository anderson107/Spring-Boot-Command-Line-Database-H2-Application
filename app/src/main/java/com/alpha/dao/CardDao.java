package com.alpha.dao;

import java.util.List;

import javax.sql.DataSource;

import com.alpha.entities.card.Card;

// interface for card CRUD operations implementation
public interface CardDao {

	public List<Card>findAll();
	public Card findCard(int id);
	public void insert(Card card);
	public void update(Card card);
	public void delete(int id);
	public void setDataSource(DataSource conn);
}
