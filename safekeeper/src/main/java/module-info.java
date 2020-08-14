module safekeeper {
	exports br.coffea.safekeeper.controller;
	exports br.coffea.safekeeper.application;
	exports br.coffea.safekeeper.util;
	opens br.coffea.safekeeper.controller;
	opens br.coffea.safekeeper.model;
	
	requires java.xml;
	requires javafx.fxml;
	requires transitive javafx.controls;
	requires javafx.base;
	requires java.sql;
}