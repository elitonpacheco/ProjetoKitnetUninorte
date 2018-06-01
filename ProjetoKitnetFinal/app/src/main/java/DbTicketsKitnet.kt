package com.example.elitonpacheco.projetokitnet

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


val DATABASE_NAME_TICKET = "DBTicket"
val TABLE_NAME_TICKET = "Tickets"
val COL_ID_TICKET = "id_ticket"
val COL_DESC_TICKET = "desc_ticket"
val COL_DATA_TICKET = "data_ticket"
val COL_STATUS_TICKET  = "status_ticket"
val COL_INQUILINO_TICKET = "inquilino_ticket"


class DbTicketsKitnet(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME_TICKET   ,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME_TICKET + " (" + COL_ID_TICKET + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_DESC_TICKET + " VARCHAR(1000)," + COL_INQUILINO_TICKET + " VARCHAR(50)," + COL_DATA_TICKET + " VARCHAR(50)," + COL_STATUS_TICKET + " VARCHAR(15))";

        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun inserirDados(cadtickets: Cad_Tickets) {

        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_DESC_TICKET, cadtickets.desc_ticket)
        cv.put(COL_STATUS_TICKET, cadtickets.status_ticket)
        cv.put(COL_DATA_TICKET,cadtickets.data_ticket)
        cv.put(COL_INQUILINO_TICKET,cadtickets.inquilino_ticket)

        var result = db.insert(TABLE_NAME_TICKET, null, cv)

        if (result == -1.toLong()) {
            Toast.makeText(context, "Falha ao Inserir os Dados", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Dados Inseridos com Sucesso", Toast.LENGTH_SHORT).show()
        }


    }

    fun consultaId() : MutableList<Cad_Tickets> {
        var lista: MutableList<Cad_Tickets> = ArrayList()

        val db = this.readableDatabase
        val query = "Select id_ticket from " + TABLE_NAME_TICKET
        val resultado = db.rawQuery(query, null)
        if (resultado.moveToFirst()) {
            do {
                var ticket = Cad_Tickets()
                ticket.id_ticket = resultado.getString(resultado.getColumnIndex(COL_ID_TICKET)).toLong()
                lista.add(ticket)
            } while (resultado.moveToNext())
        }
        resultado.close()
        db.close()
        return lista
    }


    fun consultaDadosPendentes(): MutableList<Cad_Tickets> {
        var lista: MutableList<Cad_Tickets> = ArrayList()

        val db = this.readableDatabase
        val query = "SELECT * FROM " + TABLE_NAME_TICKET + " WHERE status_ticket = 'ABERTO'"
        val resultado = db.rawQuery(query, null)
        if (resultado.moveToFirst()) {
            do {
                var cadtickets = Cad_Tickets()
                cadtickets.id_ticket = resultado.getString(resultado.getColumnIndex(COL_ID_TICKET)).toLong()
                cadtickets.desc_ticket = resultado.getString(resultado.getColumnIndex(COL_DESC_TICKET))
                cadtickets.status_ticket = resultado.getString(resultado.getColumnIndex(COL_STATUS_TICKET))
                cadtickets.data_ticket = resultado.getString(resultado.getColumnIndex(COL_DATA_TICKET))
                cadtickets.inquilino_ticket = resultado.getString(resultado.getColumnIndex(COL_INQUILINO_TICKET))
                lista.add(cadtickets)
            } while (resultado.moveToNext())
        }
        resultado.close()
        db.close()
        return lista
    }

    fun consultaDados(): MutableList<Cad_Tickets> {
        var lista: MutableList<Cad_Tickets> = ArrayList()

        val db = this.readableDatabase
        val query = "SELECT * FROM " + TABLE_NAME_TICKET
        val resultado = db.rawQuery(query, null)
        if (resultado.moveToFirst()) {
            do {
                var cadtickets = Cad_Tickets()
                cadtickets.id_ticket = resultado.getString(resultado.getColumnIndex(COL_ID_TICKET)).toLong()
                cadtickets.desc_ticket = resultado.getString(resultado.getColumnIndex(COL_DESC_TICKET))
                cadtickets.status_ticket = resultado.getString(resultado.getColumnIndex(COL_STATUS_TICKET))
                cadtickets.data_ticket = resultado.getString(resultado.getColumnIndex(COL_DATA_TICKET))
                cadtickets.inquilino_ticket = resultado.getString(resultado.getColumnIndex(COL_INQUILINO_TICKET))
                lista.add(cadtickets)
            } while (resultado.moveToNext())
        }
        resultado.close()
        db.close()
        return lista
    }

    fun atualizadadospend(id_ticket: String, statusticket:String): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_STATUS_TICKET, statusticket)
        db.update(TABLE_NAME_TICKET, cv, "id_ticket = ?", arrayOf(id_ticket))

        return true
    }


    fun deletaDados(ticket: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME_TICKET, "id_ticket = ?", arrayOf(ticket))
    }
}