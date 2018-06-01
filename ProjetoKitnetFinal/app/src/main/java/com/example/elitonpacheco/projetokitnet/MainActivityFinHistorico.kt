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
import android.widget.*
import java.util.*

class MainActivityFinHistorico : AppCompatActivity() {
    var context: Context? = null
    var listahistcontas: ListView? = null
    var listaadapter: ListaContahistAdapter? = null
    var db = DbContasReceber(this)
    var resultado = ArrayList<ContasReceber>()
    var histconta: Dialog? = null
    var posicao = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fin_historico)

        resultado = generateData()

        listahistcontas = findViewById<ListView>(R.id.lsthistcontas)
        listaadapter = ListaContahistAdapter(this, resultado)
        listahistcontas?.adapter = listaadapter


        histconta = Dialog(this)
        histconta!!.setContentView(R.layout.lista_contas)
        listahistcontas?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            if (histconta != null) {
                if (!histconta!!.isShowing) {
                    posicao = position
                } else {
                    histconta!!.dismiss()
                }
            }
        }
        listaadapter?.notifyDataSetChanged()


    }

    private fun generateData(): ArrayList<ContasReceber> {

        var dados = db.consultaDados()

        for (i in 0..(dados.size - 1)) {
            var usuario = ContasReceber(dados.get(i).id_conta, dados.get(i).inquilino_conta, dados.get(i).valor_conta, dados.get(i).status_conta,dados.get(i).data_conta)
            resultado.add(usuario)
        }
        return resultado
    }

    inner class ListaContahistAdapter : BaseAdapter {

        var ListahistContas: ArrayList<ContasReceber>
        private var context: Context? = null

        constructor(context: Context, resultado: ArrayList<ContasReceber>) : super() {
            this.ListahistContas  = resultado
            this.context = context
        }


        @RequiresApi(Build.VERSION_CODES.O)
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view: View?
            val viewHolder: ViewHolder

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.lista_ger_contas, null)
                viewHolder = ViewHolder(view)
                view?.tag = viewHolder
                Log.i("JSA", "set Tag for ViewHolder, position: " + position)
            } else {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }

            viewHolder.idconta?.text ="ID CONTA: " + ListahistContas[position].id_conta.toString()
            viewHolder.inquilinonome?.text = "Locatário: " + ListahistContas[position].inquilino_conta
            viewHolder.dataconta?.text = "Data Recibo: " + ListahistContas[position].data_conta
            viewHolder.valorconta?.text = "R$ " + ListahistContas[position].valor_conta.toString().replace(".",",")
            viewHolder.statusconta?.text = "Status: " + ListahistContas[position].status_conta


            return view
        }


        override fun getItem(position: Int): ContasReceber{
            return ListahistContas[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return ListahistContas.size
        }
    }

    private inner class ViewHolder(row: View?) {
        var idconta: TextView? = null
        var inquilinonome: TextView? = null
        var valorconta: TextView? = null
        var dataconta: TextView? = null
        var statusconta: TextView? = null



        init {
            this.idconta = row?.findViewById<TextView>(R.id.idconta_ger)
            this.inquilinonome = row?.findViewById<TextView>(R.id.nomeinquilino_ger)
            this.valorconta = row?.findViewById<TextView>(R.id.valorconta_ger)
            this.dataconta = row?.findViewById<TextView>(R.id.dataconta_ger)
            this.statusconta = row?.findViewById<TextView>(R.id.statusconta_ger)

        }
    }
}
