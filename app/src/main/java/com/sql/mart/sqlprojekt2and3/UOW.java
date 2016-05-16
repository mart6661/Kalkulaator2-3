package com.sql.mart.sqlprojekt2and3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Mart on 5/16/2016.
 */
public class UOW {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private final Context context;

    public OperationTypesRepo operationTypesRepo;
    public StatisticsRepo statisticsRepo;
    public OperationsRepo operationsRepo;

    public UOW(Context context) {
        this.context = context;

        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();

        operationTypesRepo = new OperationTypesRepo(database, dbHelper.TABLE_OPERATIONTYPES, dbHelper.ALLCOLUMNS_OPERATIONTYPES);
        statisticsRepo = new StatisticsRepo(database, dbHelper.TABLE_STATISTICS, dbHelper.ALLCOLUMNS_STATISTICS, operationTypesRepo);
        operationsRepo = new OperationsRepo(database, dbHelper.TABLE_OPERATIONS, dbHelper.ALLCOLUMNS_OPERATIONS, operationTypesRepo);
    }

    public void DropCreateDatabase() { dbHelper.dropCreateDatabase(database); }

    public void setOperationTypes() {

        if(operationTypesRepo.getAll().isEmpty()) {
            OperationTypes plus = operationTypesRepo.add(new OperationTypes("+", 0));
            OperationTypes minus = operationTypesRepo.add(new OperationTypes("-", 0));
            OperationTypes multiply = operationTypesRepo.add(new OperationTypes("*", 0));
            OperationTypes divide = operationTypesRepo.add(new OperationTypes("/", 0));
        }
    }

    public void addToDatabase(Double num1, Double num2, String operant, Double answer) {
        OperationTypes operationTypes = operationTypesRepo.getCounterFromOperant(operant);
        operationTypes.setLifetimeCounter(operationTypes.getLifetimeCounter()+1);
        operationTypesRepo.update(operationTypes);
        statisticsRepo.addOneToDayCounter(operationTypes.getId());

        Operations operations = new Operations();
        operations.setOperantId(operationTypes.getId());
        operations.setNum1(num1);
        operations.setNum2(num2);
        operations.setRes(answer);
        operations.setTimestamp(System.currentTimeMillis());

        //operationsRepo.add(new Operations(operationTypes.getId(), num1, num2, answer, operations.setTimestamp(System.currentTimeMillis())));

        operationsRepo.add(operations);

    }


}
