package com.example.elitonpacheco.projetokitnet

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast




    val DATABASE_NAME_CONTAS = "DBContasReceber"
    val TABLE_NAME_CONTAS = "Contasreceber"
    val COL_ID_CONTA = "id_conta"
    val COL_INQUILINO_CONTA = "nome_inquilino_conta"
    val COL_VALOR_CONTA = "valor_conta"
    val COL_STATUS_CONTA = "status_conta"
    val COL_DATA_CONTA = "data_conta"

    class DbContasReceber(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME_CONTAS,null,1) {
        override fun onCreate(db: SQLiteDatabase?) {

            val createTable = "CREATE TABLE " + TABLE_NAME_CONTAS + " (" + COL_ID_CONTA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_INQUILINO_CONTA + " VARCHAR(50)," + COL_VALOR_CONTA +
                    " REAL(20)," + COL_STATUS_CONTA + " VARCHAR(15)," + COL_DATA_CONTA + " VARCHAR(15))";

            db?.execSQL(createTable)

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        fun inserirDados(contareceber: ContasReceber) {

            val db = this.writableDatabase
            var cv = ContentValues()
            cv.put(COL_INQUILINO_CONTA, contareceber.inquilino_conta)
            cv.put(COL_VALOR_CONTA, contareceber.valor_conta)
            cv.put(COL_STATUS_CONTA, contareceber.status_conta)
            cv.put(COL_DATA_CONTA, contareceber.data_conta)

            var result = db.insert(TABLE_NAME_CONTAS, null, cv)

            if (result == -1.toLong()) {
                Toast.makeText(context, "Falha ao Inserir os Dados", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Dados Inseridos com Sucesso", Toast.LENGTH_SHORT).show()
            }


        }


        fun consultaDadosPendentes(): MutableList<ContasReceber> {
            var lista: MutableList<ContasReceber> = ArrayList()

            val db = this.readableDatabase
            val query = "SELECT * FROM " + TABLE_NAME_CONTAS + " WHERE status_conta = 'PENDENTE'"
            val resultado = db.rawQuery(query, null)
            if (resultado.moveToFirst()) {
                do {
                    var contareceber = ContasReceber()
                    contareceber.id_conta = resultado.getString(resultado.getColumnIndex(COL_ID_CONTA)).toLong()
                    contareceber.inquilino_conta= resultado.getString(resultado.getColumnIndex(COL_INQUILINO_CONTA))
                    contareceber.valor_conta = resultado.getString(resultado.getColumnIndex(COL_VALOR_CONTA)).toDouble()
                    contareceber.status_conta = resultado.getString(resultado.getColumnIndex(COL_STATUS_CONTA))
                    contareceber.data_conta = resultado.getString(resultado.getColumnIndex(COL_DATA_CONTA))
                    lista.add(contareceber)
                } while (resultado.moveToNext())
            }
            resultado.close()
            db.close()
            return lista
        }

        fun consultaDados(): MutableList<ContasReceber> {
            var lista: MutableList<ContasReceber> = ArrayList()

            val db = this.readableDatabase
            val query = "SELECT * FROM " + TABLE_NAME_CONTAS
            val resultado = db.rawQuery(query, null)
            if (resultado.moveToFirst()) {
                do {
                    var contareceber = ContasReceber()
                    contareceber.id_conta = resultado.getString(resultado.getColumnIndex(COL_ID_CONTA)).toLong()
                    contareceber.inquilino_conta= resultado.getString(resultado.getColumnIndex(COL_INQUILINO_CONTA))
                    contareceber.valor_conta = resultado.getString(resultado.getColumnIndex(COL_VALOR_CONTA)).toDouble()
                    contareceber.status_conta = resultado.getString(resultado.getColumnIndex(COL_STATUS_CONTA))
                    contareceber.data_conta = resultado.getString(resultado.getColumnIndex(COL_DATA_CONTA))
                    lista.add(contareceber)
                } while (resultado.moveToNext())
            }
            resultado.close()
            db.close()
            return lista
        }

        fun atualizadadospend(id_conta: String, statusconta:String): Boolean {
            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put(COL_STATUS_CONTA, statusconta)
            db.update(TABLE_NAME_CONTAS, cv, "id_conta = ?", arrayOf(id_conta))

            return true
        }

        fun atualizadadoscompleto(id_conta: String, statusconta:String): Boolean {
            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put(COL_STATUS_CONTA, statusconta)
            db.update(TABLE_NAME_CONTAS, cv, "id_conta = ?", arrayOf(id_conta))

            return true
        }


        fun deletaDados(Conta: String): Int {
            val db = this.writableDatabase
            return db.delete(TABLE_NAME_CONTAS, "id_conta = ?", arrayOf(Conta))
        }
    }