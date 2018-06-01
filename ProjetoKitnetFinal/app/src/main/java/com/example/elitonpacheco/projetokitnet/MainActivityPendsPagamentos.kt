package com.example.elitonpacheco.projetokitnet

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*

class MainActivityPendsPagamentos : AppCompatActivity() {
    val mLocale = Locale("pt", "BR")
    var context: Context? = null
    var listacontas: ListView? = null
    var listaadapter: ListaContaAdapter? = null
    var db = DbContasReceber(this)
    var resultado = ArrayList<ContasReceber>()
    var adapter: ArrayAdapter<ContasReceber>? = null
    var editaconta: Dialog? = null
    var posicao = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_pends_pagamentos)

        resultado = generateData()

        listacontas = findViewById<ListView>(R.id.lstcontaspend)
        listaadapter = ListaContaAdapter(this, resultado)
        listacontas?.adapter = listaadapter


        editaconta = Dialog(this)
        editaconta!!.setContentView(R.layout.edita_contas)
        listacontas?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            if (editaconta != null) {
                if (!editaconta!!.isShowing) {
                    displayInputDialog(position)
                    posicao = position
                } else {
                    editaconta!!.dismiss()
                }
            }
        }
        listaadapter?.notifyDataSetChanged()
    }

    private fun generateData(): ArrayList<ContasReceber> {

        var dados = db.consultaDadosPendentes()

        for (i in 0..(dados.size - 1)) {
            var usuario = ContasReceber(dados.get(i).id_conta, dados.get(i).inquilino_conta, dados.get(i).data_conta, dados.get(i).valor_conta)
            resultado.add(usuario)
        }
        return resultado
    }

    inner class ListaContaAdapter : BaseAdapter {

        var ListaContas: ArrayList<ContasReceber>
        private var context: Context? = null

        constructor(context: Context, resultado: ArrayList<ContasReceber>) : super() {
            this.ListaContas  = resultado
            this.context = context
        }


        @RequiresApi(Build.VERSION_CODES.O)
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view: View?
            val viewHolder: ViewHolder

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.lista_contas, null)
                viewHolder = ViewHolder(view)
                view?.tag = viewHolder
                Log.i("JSA", "set Tag for ViewHolder, position: " + position)
            } else {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }

            viewHolder.idconta?.text ="ID CONTA: " + ListaContas[position].id_conta.toString()
            viewHolder.inquilinonome?.text = "Locatário: " + ListaContas[position].inquilino_conta
            viewHolder.dataconta?.text = "Data Recibo: " + ListaContas[position].data_conta
            viewHolder.valorconta?.text = "R$ " + ListaContas[position].valor_conta.toString().replace(".",",")



            return view
        }


        override fun getItem(position: Int): ContasReceber{
            return ListaContas[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return ListaContas.size
        }
    }

    private inner class ViewHolder(row: View?) {
        var idconta: TextView? = null
        var inquilinonome: TextView? = null
        var valorconta: TextView? = null
        var dataconta: TextView? = null



        init {
            this.idconta = row?.findViewById<TextView>(R.id.idconta)
            this.inquilinonome = row?.findViewById<TextView>(R.id.nomeinquilino)
            this.valorconta = row?.findViewById<TextView>(R.id.valorconta)
            this.dataconta = row?.findViewById<TextView>(R.id.dataconta)

        }
    }

    private fun displayInputDialog(pos: Int) {
        editaconta = Dialog(this)
        editaconta!!.setContentView(R.layout.edita_contas)

        val ideditaconta: TextView = editaconta!!.findViewById(R.id.idcontaedita)
        val locatario: TextView = editaconta!!.findViewById(R.id.nomeinquilinoedita)
        val dataconta: TextView = editaconta!!.findViewById(R.id.datacontaedita)
        val valorconta: TextView = editaconta!!.findViewById(R.id.valorcontaedita)
        val cancelabtn: Button = editaconta!!.findViewById(R.id.btncancelaconta)
        val atualizabtn: Button = editaconta!!.findViewById(R.id.btnatualizaconta)
        val baixarecibo: RadioButton = editaconta!!.findViewById(R.id.confirmapag)

        if (pos == -1) {
            atualizabtn.isEnabled = false
            cancelabtn.isEnabled = false
        } else {
            atualizabtn.isEnabled = true
            cancelabtn.isEnabled = true
            ideditaconta.setText("ID CONTA: " + resultado[pos].id_conta)
            locatario.setText("Locatário: " + resultado[pos].inquilino_conta)
            dataconta.setText("Data Recibo: " + resultado[pos].data_conta)
            valorconta.setText("R$ " + resultado[pos].valor_conta.toString().replace(".",","))

        }

        atualizabtn.setOnClickListener {

            val idconta = resultado[pos].id_conta
            if (baixarecibo.isChecked == true) {
                var novostatus = "PAGO"
                val msgBox = AlertDialog.Builder(this)
                msgBox.setTitle("Atualizar Conta")
                msgBox.setIcon(R.drawable.pagcontag64b)
                msgBox.setMessage("Você deseja Confirmar Pagamento?")


                msgBox.setPositiveButton("SIM", { dialogInterface: DialogInterface, i: Int ->
                    db.atualizadadospend(idconta.toString(),novostatus)
                    editaconta?.dismiss()
                    finish()
                    val telapenpag = Intent(this, MainActivityPendsPagamentos::class.java)
                    startActivity(telapenpag)
                    msgBox.setCancelable(true)
                    Toast.makeText(this, "Dados atualizados com Sucesso!!", Toast.LENGTH_LONG).show()

                })
                msgBox.setNegativeButton("NÃO", { dialogInterface: DialogInterface, i: Int ->
                    msgBox.setCancelable(true)
                    editaconta?.dismiss()
                })
                msgBox.show()

            }
        }

        cancelabtn.setOnClickListener {
            editaconta?.dismiss()
        }

        editaconta!!.show()
    }
}
