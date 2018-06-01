package com.example.elitonpacheco.projetokitnet

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import java.util.ArrayList

class MainActivityHist_Tikets : AppCompatActivity() {

    var context: Context? = null
    var listahisttickets: ListView? = null
    var listaadapter: ListaTickethistAdapter? = null
    var db = DbTicketsKitnet(this)
    var resultado = ArrayList<Cad_Tickets>()
    var histtickets: Dialog? = null
    var posicao = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_hist__tikets)

        resultado = generateData()

        listahisttickets = findViewById<ListView>(R.id.lsthisttickets)
        listaadapter = ListaTickethistAdapter(this, resultado)
        listahisttickets?.adapter = listaadapter


        histtickets = Dialog(this)
        histtickets!!.setContentView(R.layout.lista_contas)
        listahisttickets?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            if (histtickets != null) {
                if (!histtickets!!.isShowing) {
                    posicao = position
                } else {
                    histtickets!!.dismiss()
                }
            }
        }
        listaadapter?.notifyDataSetChanged()
    }

    private fun generateData(): ArrayList<Cad_Tickets> {

        var dados = db.consultaDados()

        for (i in 0..(dados.size - 1)) {
            var ticketshist = Cad_Tickets(dados.get(i).id_ticket, dados.get(i).desc_ticket, dados.get(i).status_ticket, dados.get(i).data_ticket,dados.get(i).inquilino_ticket)
            resultado.add(ticketshist)
        }
        return resultado
    }

    inner class ListaTickethistAdapter : BaseAdapter {

        var ListahistTickets: ArrayList<Cad_Tickets>
        private var context: Context? = null

        constructor(context: Context, resultado: ArrayList<Cad_Tickets>) : super() {
            this.ListahistTickets  = resultado
            this.context = context
        }


        @RequiresApi(Build.VERSION_CODES.O)
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view: View?
            val viewHolder: ViewHolder

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.lista_ticket, null)
                viewHolder = ViewHolder(view)
                view?.tag = viewHolder
                Log.i("JSA", "set Tag for ViewHolder, position: " + position)
            } else {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }

            viewHolder.idticket?.text ="ID CTICKET: " + ListahistTickets[position].id_ticket
            viewHolder.inquilinonometicket?.text = "Inquilino: " + ListahistTickets[position].inquilino_ticket
            viewHolder.dataticket?.text = "Data: " + ListahistTickets[position].data_ticket
            viewHolder.statusticket?.text = "Status: " + ListahistTickets[position].status_ticket


            return view
        }


        override fun getItem(position: Int): Cad_Tickets{
            return ListahistTickets[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return ListahistTickets.size
        }
    }

    private inner class ViewHolder(row: View?) {
        var idticket: TextView? = null
        var inquilinonometicket: TextView? = null
        //var descticket: EditText? = null
        var dataticket: TextView? = null
        var statusticket: TextView? = null



        init {
            this.idticket = row?.findViewById<TextView>(R.id.idticket)
            this.inquilinonometicket = row?.findViewById<TextView>(R.id.nomeinquilinoticket)
            this.dataticket = row?.findViewById<TextView>(R.id.dataticket)
            this.statusticket = row?.findViewById<TextView>(R.id.statusticket)

        }
    }
}
