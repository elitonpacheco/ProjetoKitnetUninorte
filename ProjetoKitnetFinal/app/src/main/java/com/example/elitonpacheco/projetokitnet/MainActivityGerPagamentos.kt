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


class MainActivityGerPagamentos : AppCompatActivity() {
    val mLocale = Locale("pt", "BR")
    var context: Context? = null
    var listatodascontas: ListView? = null
    var listaadapter: ListaContagerAdapter? = null
    var db = DbContasReceber(this)
    var resultado = ArrayList<ContasReceber>()
    var adapter: ArrayAdapter<ContasReceber>? = null
    var editatodasconta: Dialog? = null
    var posicao = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ger_pagamentos)

        resultado = generateData()

        listatodascontas = findViewById<ListView>(R.id.lsttodascontas)
        listaadapter = ListaContagerAdapter(this, resultado)
        listatodascontas?.adapter = listaadapter


        editatodasconta = Dialog(this)
        editatodasconta!!.setContentView(R.layout.edita_ger_contas)
        listatodascontas?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            if (editatodasconta != null) {
                if (!editatodasconta!!.isShowing) {
                    displayInputDialog(position)
                    posicao = position
                } else {
                    editatodasconta!!.dismiss()
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

    inner class ListaContagerAdapter : BaseAdapter {

        var ListatodasContas: ArrayList<ContasReceber>
        private var context: Context? = null

        constructor(context: Context, resultado: ArrayList<ContasReceber>) : super() {
            this.ListatodasContas  = resultado
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

            viewHolder.idconta?.text ="ID CONTA: " + ListatodasContas[position].id_conta.toString()
            viewHolder.inquilinonome?.text = "Locatário: " + ListatodasContas[position].inquilino_conta
            viewHolder.dataconta?.text = "Data Recibo: " + ListatodasContas[position].data_conta
            viewHolder.valorconta?.text = "R$ " + ListatodasContas[position].valor_conta.toString().replace(".",",")
            viewHolder.statusconta?.text = "Status: " + ListatodasContas[position].status_conta


            return view
        }


        override fun getItem(position: Int): ContasReceber{
            return ListatodasContas[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return ListatodasContas.size
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

    private fun displayInputDialog(pos: Int) {
        editatodasconta = Dialog(this)
        editatodasconta!!.setContentView(R.layout.edita_ger_contas)

        val ideditaconta: TextView = editatodasconta!!.findViewById(R.id.idcontager)
        val locatario: TextView = editatodasconta!!.findViewById(R.id.nomeinquilinoger)
        val dataconta: TextView = editatodasconta!!.findViewById(R.id.datacontager)
        val valorconta: EditText = editatodasconta!!.findViewById(R.id.valorcontager)
        val statusconta: TextView = editatodasconta!!.findViewById(R.id.statuscontager)
        val deletabtn: Button = editatodasconta!!.findViewById(R.id.btndeletacontager)
        val atualizabtn: Button = editatodasconta!!.findViewById(R.id.btnatualizacontager)
        val alterarecibopago: RadioButton = editatodasconta!!.findViewById(R.id.alterapagoger)
        val alterarecibopendente: RadioButton = editatodasconta!!.findViewById(R.id.alterapendenteger)

        if (pos == -1) {
            atualizabtn.isEnabled = false
            deletabtn.isEnabled = false
        } else {
            atualizabtn.isEnabled = true
            deletabtn.isEnabled = true
            ideditaconta.setText("ID CONTA: " + resultado[pos].id_conta)
            locatario.setText("Locatário: " + resultado[pos].inquilino_conta)
            dataconta.setText("Data Recibo: " + resultado[pos].data_conta)
            valorconta.setText("R$ " + resultado[pos].valor_conta.toString().replace(".",","))
            statusconta.setText("Status: " + resultado[pos].status_conta)



        }

        atualizabtn.setOnClickListener {
            var novostatus = ""
            val idconta = resultado[pos].id_conta
            if (alterarecibopago.isChecked == true) {
                novostatus = "PAGO"
            }
            if (alterarecibopendente.isChecked == true) {
                novostatus = "PENDENTE"
            }

            if(novostatus == ""){
                Toast.makeText(this,"Favor Selecionar um status para Conta",Toast.LENGTH_SHORT).show()
            }else {
                val msgBox = AlertDialog.Builder(this)
                msgBox.setTitle("Atualizar Conta")
                msgBox.setIcon(R.drawable.iconcontas64b)
                msgBox.setMessage("Você deseja Atualizar o Recibo?")


                msgBox.setPositiveButton("SIM", { dialogInterface: DialogInterface, i: Int ->
                    db.atualizadadoscompleto(idconta.toString(), novostatus)
                    editatodasconta?.dismiss()
                    finish()
                    val telagerpag = Intent(this, MainActivityGerPagamentos::class.java)
                    startActivity(telagerpag)
                    msgBox.setCancelable(true)
                    Toast.makeText(this, "Dados atualizados com Sucesso!!", Toast.LENGTH_LONG).show()

                })
                msgBox.setNegativeButton("NÃO", { dialogInterface: DialogInterface, i: Int ->
                    msgBox.setCancelable(true)
                    editatodasconta?.dismiss()
                })
                msgBox.show()
            }

        }

        deletabtn.setOnClickListener {
            val msgBox = AlertDialog.Builder(this)
            msgBox.setTitle("Excluir Conta")
            msgBox.setIcon(R.drawable.icodeleta64)
            msgBox.setMessage("Você deseja excluir a Conta?")


            msgBox.setPositiveButton("SIM", { dialogInterface: DialogInterface, i: Int ->
                db.deletaDados(resultado[pos].id_conta.toString())
                delete(posicao)
                editatodasconta?.dismiss()
                finish()
                val telagerpag = Intent(this, MainActivityGerPagamentos::class.java)
                startActivity(telagerpag)
                Toast.makeText(this, "Conta excluida com sucesso!!", Toast.LENGTH_LONG).show()
                msgBox.setCancelable(true)

            })
            msgBox.setNegativeButton("NÃO", { dialogInterface: DialogInterface, i: Int ->
                msgBox.setCancelable(true)
                editatodasconta?.dismiss()
            })
            msgBox.show()

        }

        editatodasconta!!.show()
    }

        fun delete(position: Int): Boolean? {
            try {
                resultado.removeAt(position)

                return true
            } catch (e: Exception) {
                e.printStackTrace()
                return false

            }

        }
}
