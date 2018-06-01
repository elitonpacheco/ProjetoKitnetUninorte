package com.example.elitonpacheco.projetokitnet

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


    val DATABASE_NAME = "KitnetDb"
    val TABLE_NAME = "CadInquilino"
    val COL_ID_INQUILINO = "id_inquilino"
    val COL_NOME_INQUILINO = "nome_inquilino"
    val COL_EMAIL_INQUILINO = "email_inquilino"
    val COL_CPF_INQUILINO = "cpf_inquilino"
    val COL_CELULAR_INQUILINO = "celular_inquilino"
    val COL_SENHA_INQUILINO = "senha_inquilino"
    val COL_STATUS_INQUILINO = "status_inquilino"
    val COL_TIPO_INQUILINO = "tipo_inquilino"

class DataBaseHandler(var context:Context):SQLiteOpenHelper(context, DATABASE_NAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME +" (" + COL_ID_INQUILINO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NOME_INQUILINO + " VARCHAR(50)," + COL_EMAIL_INQUILINO +
                " VARCHAR(50)," + COL_CPF_INQUILINO + " VARCHAR(50)," + COL_CELULAR_INQUILINO +
                " VARCHAR(15)," + COL_SENHA_INQUILINO + " VARCHAR(50)," +
                COL_STATUS_INQUILINO + " VARCHAR(50)," +
                COL_TIPO_INQUILINO + " VARCHAR(50))";

        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun inserirDados(inquilino:Cad_Inquilino){

        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NOME_INQUILINO,inquilino.nome_inquilino)
        cv.put(COL_EMAIL_INQUILINO,inquilino.email_inquilino)
        cv.put(COL_CPF_INQUILINO,inquilino.cpf_inquilino)
        cv.put( COL_CELULAR_INQUILINO,inquilino.celular_inquilino)
        cv.put(COL_SENHA_INQUILINO,inquilino.senha_inquilino)
        cv.put(COL_STATUS_INQUILINO,inquilino.status_inquilino)
        cv.put(COL_TIPO_INQUILINO,inquilino.tipo_inquilino)
        var result =  db.insert(TABLE_NAME,null,cv)

        if(result ==-1.toLong()) {
            Toast.makeText(context,"Falha ao Inserir os Dados",Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context,"Dados Inseridos com Sucesso",Toast.LENGTH_SHORT).show()
        }


    }


    fun consultaDados() : MutableList<Cad_Inquilino>{
        var lista : MutableList<Cad_Inquilino> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val resultado = db.rawQuery(query,null)
        if(resultado.moveToFirst()){
            do{
                var inquilino = Cad_Inquilino()
                inquilino.id_inquilino = resultado.getString(resultado.getColumnIndex(COL_ID_INQUILINO)).toLong()
                inquilino.nome_inquilino = resultado.getString(resultado.getColumnIndex(COL_NOME_INQUILINO))
                inquilino.email_inquilino = resultado.getString(resultado.getColumnIndex(COL_EMAIL_INQUILINO))
                inquilino.cpf_inquilino = resultado.getString(resultado.getColumnIndex(COL_CPF_INQUILINO))
                inquilino.celular_inquilino = resultado.getString(resultado.getColumnIndex(COL_CELULAR_INQUILINO))
                inquilino.senha_inquilino = resultado.getString(resultado.getColumnIndex(COL_SENHA_INQUILINO))
                inquilino.status_inquilino = resultado.getString(resultado.getColumnIndex(COL_STATUS_INQUILINO))
                inquilino.tipo_inquilino = resultado.getString(resultado.getColumnIndex(COL_TIPO_INQUILINO))
                lista.add(inquilino)
            }while(resultado.moveToNext())
        }


        resultado.close()
        db.close()
        return lista
    }

    fun consultaInquilinos() : MutableList<Get_Inquilinos>{
        var lista : MutableList<Get_Inquilinos> = ArrayList()

        val db = this.readableDatabase
        val query = "Select nome_inquilino from " + TABLE_NAME
        val resultado = db.rawQuery(query,null)
        if(resultado.moveToFirst()){
            do{
                var inquilino = Get_Inquilinos()
                inquilino.nome_inquilino = resultado.getString(resultado.getColumnIndex(COL_NOME_INQUILINO))
                lista.add(inquilino)
            }while(resultado.moveToNext())
        }


        resultado.close()
        db.close()
        return lista
    }

    fun ValidaLogin() : MutableList<Get_Inquilinos>{
        var lista : MutableList<Get_Inquilinos> = ArrayList()

        val db = this.readableDatabase
        val query = "Select id_inquilino, nome_inquilino, cpf_inquilino, email_inquilino, senha_inquilino, tipo_inquilino from " + TABLE_NAME
        val resultado = db.rawQuery(query,null)
        if(resultado.moveToFirst()){
            do{
                var inquilino = Get_Inquilinos()
                inquilino.id_inquilino = resultado.getString(resultado.getColumnIndex(COL_ID_INQUILINO)).toLong()
                inquilino.nome_inquilino = resultado.getString(resultado.getColumnIndex(COL_NOME_INQUILINO))
                inquilino.senha_inquilino = resultado.getString(resultado.getColumnIndex(COL_SENHA_INQUILINO))
                inquilino.email_inquilino = resultado.getString(resultado.getColumnIndex(COL_EMAIL_INQUILINO))
                inquilino.cpf_inquilino = resultado.getString(resultado.getColumnIndex(COL_CPF_INQUILINO))
                inquilino.tipo_inquilino = resultado.getString(resultado.getColumnIndex(COL_TIPO_INQUILINO))
                lista.add(inquilino)
            }while(resultado.moveToNext())
        }


        resultado.close()
        db.close()
        return lista
    }

    fun atualizadados(id_inquilino:String,email:String,celular:String):Boolean{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_EMAIL_INQUILINO,email)
        cv.put(COL_CELULAR_INQUILINO,celular)
        db.update(TABLE_NAME,cv,"id_inquilino = ?", arrayOf(id_inquilino))

        return true
    }


    fun deletaDados(inquilino:String):Int{
        val db = this.writableDatabase
        return db.delete(TABLE_NAME,"id_inquilino = ?", arrayOf(inquilino))
    }



}