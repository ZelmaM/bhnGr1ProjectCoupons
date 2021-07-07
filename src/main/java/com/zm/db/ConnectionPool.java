/*
Creating by Zelma Milev
*/
package com.zm.db;

import com.zm.utils.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {

    private static final int CONNECTION_POOL_SIZE = 10;
    private static Stack<Connection> connections;
    private static ConnectionPool instance;

    private ConnectionPool() throws SQLException {
        initializePool();
    }

    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    private void initializePool() throws SQLException {
        connections = new Stack<>();

        for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
            connections.push(DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS));
        }
    }

    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            if (connections.isEmpty()) {
                connections.wait();
            }
        }
        return connections.pop();
    }

    public void restoreConnection(Connection connection) {
        synchronized (connections) {
            connections.push(connection);
            connections.notify();
        }
    }

    public void closeAllConnections() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < CONNECTION_POOL_SIZE) {
                wait();
            }
            connections.removeAllElements();
            System.out.println("ALL CONNECTIONS CLOSED");
        }
    }
}
