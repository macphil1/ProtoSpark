package com.thegreenseek.spark.protos;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.*;

import java.util.ArrayList;
import java.util.List;

public class SimpleTextProcessor {

    public static void main(String [] args) {

        try {

            if (args[0] == null || args[1] == null) {
                System.out.println("PLEASE PROVIDE MASTER --MASTER URL-- AND APPLICATION NAME --NAME--");
                return;
            }

            String master = args[0];
            String appname = args[1];
            String textFilePath = args[2];


            //Init Spark Session
            SparkSession spark = SparkSession.builder()
                    .master(master)
                    .appName(appname)
                    .getOrCreate();


            List<StructField> fields = new ArrayList<>();

            fields.add(DataTypes.createStructField("nofinessej", DataTypes.StringType, true));
            fields.add(DataTypes.createStructField("rsej", DataTypes.StringType, true));
            fields.add(DataTypes.createStructField("eml", DataTypes.StringType, true));
            fields.add(DataTypes.createStructField("libeml", DataTypes.StringType, true));
            fields.add(DataTypes.createStructField("noautor", DataTypes.StringType, true));
            fields.add(DataTypes.createStructField("dateautor", DataTypes.StringType, true));
            fields.add(DataTypes.createStructField("nofinesset", DataTypes.StringType, true));
            fields.add(DataTypes.createStructField("datemeo", DataTypes.StringType, true));
            fields.add(DataTypes.createStructField("datefin", DataTypes.StringType, true));

            StructType csvSchema = DataTypes.createStructType(fields);

            //Load text file fron filesystem
            /*Dataset<Row> rawcsv = spark.read().format("csv")
                    .option("header", "true")
                    .option("delimiter",";")
                    .option("encoding","UTF-8")
                    .schema("nofinessej STRING," +
                    "rsej STRING," +
                    "eml STRING," +
                    "libeml STRING," +
                    "noautor STRING," +
                    "dateautor DATE," +
                    "nofinesset STRING," +
                    "datemeo DATE," +
                    "datefin DATE")
                    .load(textFilePath);*/
            Dataset<Row> rawcsv = spark.read().format("csv")
                    .option("header", "true")
                    .option("delimiter",";")
                    .option("encoding","UTF-8")
                    .schema(csvSchema)
                    .load(textFilePath);


            rawcsv.printSchema();
            rawcsv.show(false);
        }catch (ArrayIndexOutOfBoundsException ex) {
            System.out.print("ARGS 0 : " + "args[0] - EMPTY");
        }

    }
}
