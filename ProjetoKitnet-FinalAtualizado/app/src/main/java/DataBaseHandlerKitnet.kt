package com.example.elitonpacheco.projetokitnet

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME_KITNET ="CadKitnetDB"
val TABLE_NAME_KITNET = "CadKitnet"
val COL_ID_KITNET = "id_kitnet"
val COL_NOME_KITNET = "nome_kitnet"
val COL_QTD_QUARTOS = "qtd_quartos"
val COL_NUM_KITNET = "num_kitnet"
val COL_STATUS_KITNET = "status_kitnet"
val COL_INQUILINO_KITNET = "inquilino_kitnet"


class DataBaseHandlerKitnet(var context:Context):SQLiteOpenHelper(context, DATABASE_NAME_KITNET,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME_KITNET + " (" + COL_ID_KITNET + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NOME_KITNET + " VARCHAR(50)," + COL_QTD_QUARTOS +
                " VARCHAR(5)," + COL_NUM_KITNET + " VARCHAR(5)," + COL_STATUS_KITNET +
                " VARCHAR(20)," + COL_INQUILINO_KITNET + " VARCHAR(50))";

        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun inserirDados(kitnet: cad_kitnet) {

        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NOME_KITNET, kitnet.nome_kitnet)
        cv.put(COL_QTD_QUARTOS, kitnet.qtd_quartos)
        cv.put(COL_NUM_KITNET, kitnet.num_kitnet)
        cv.put(COL_STATUS_KITNET, kitnet.status_kitnet)
        cv.put(COL_INQUILINO_KITNET,kitnet.inquilino_kitnet)
        var result = db.insert(TABLE_NAME_KITNET, null, cv)

        if (result == -1.toLong()) {
            Toast.makeText(context, "Falha ao Inserir os Dados", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Dados Inseridos com Sucesso", Toast.LENGTH_SHORT).show()
        }


    }


    fun consultaDados(): MutableList<cad_kitnet> {
        var lista: MutableList<cad_kitnet> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME_KITNET
        val resultado = db.rawQuery(query, null)
        if (resultado.moveToFirst()) {
            do {
                var kitnetcad = cad_kitnet()
                kitnetcad.id_kitnet = resultado.getString(resultado.getColumnIndex(COL_ID_KITNET)).toLong()
                kitnetcad.nome_kitnet = resultado.getString(resultado.getColumnIndex(COL_NOME_KITNET))
                kitnetcad.qtd_quartos = resultado.getString(resultado.getColumnIndex(COL_QTD_QUARTOS))
                kitnetcad.num_kitnet = resultado.getString(resultado.getColumnIndex(COL_NUM_KITNET))
                kitnetcad.status_kitnet = resultado.getString(resultado.getColumnIndex(COL_STATUS_KITNET))
                kitnetcad.inquilino_kitnet = resultado.getString(resultado.getColumnIndex(COL_INQUILINO_KITNET))
                lista.add(kitnetcad)
            } while (resultado.moveToNext())
        }

        resultado.close()
        db.close()
        return lista
    }


    fun atualizaDadoskitnet(idkitnnet:String,novonome:String,novoqtdquartos:String,novonumkitnet:String,novoinquilino:String,novostatuskitnet:String):Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()

                cv.put(COL_NOME_KITNET,novonome)
                cv.put(COL_QTD_QUARTOS,novoqtdquartos)
                cv.put(COL_NUM_KITNET,novonumkitnet)
                cv.put(COL_INQUILINO_KITNET,novoinquilino)
                cv.put(COL_STATUS_KITNET,novostatuskitnet)
                db.update(TABLE_NAME_KITNET,cv,"id_kitnet = ?", arrayOf(idkitnnet))

        return true
    }

    fun deletaDados(id_kitnet:String):Int{
        val db = this.writableDatabase
        return db.delete(TABLE_NAME_KITNET,"id_kitnet = ?", arrayOf(id_kitnet))
    }
}


