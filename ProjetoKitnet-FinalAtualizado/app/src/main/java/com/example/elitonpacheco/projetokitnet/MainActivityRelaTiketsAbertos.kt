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

class MainActivityRelaTiketsAbertos : AppCompatActivity() {

    val mLocale = Locale("pt", "BR")
    var context: Context? = null
    var listatodostickets: ListView? = null
    var listaadapter: ListaTicketgerAdapter? = null
    var db = DbTicketsKitnet(this)
    var resultado = ArrayList<Cad_Tickets>()
    var adapter: ArrayAdapter<Cad_Tickets>? = null
    var editatodostickets: Dialog? = null
    var posicao = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_rela_tikets_abertos)

        resultado = generateData()

        listatodostickets = findViewById<ListView>(R.id.lstticketsabertos)
        listaadapter = ListaTicketgerAdapter(this, resultado)
        listatodostickets?.adapter = listaadapter


        editatodostickets = Dialog(this)
        editatodostickets!!.setContentView(R.layout.edita_ticket)
        listatodostickets?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            if (editatodostickets != null) {
                if (!editatodostickets!!.isShowing) {
                    displayInputDialog(position)
                    posicao = position
                } else {
                    editatodostickets!!.dismiss()
                }
            }
        }
        listaadapter?.notifyDataSetChanged()
    }

    private fun generateData(): ArrayList<Cad_Tickets> {

        var dados = db.consultaDadosPendentes()

        for (i in 0..(dados.size - 1)) {
            var usuario = Cad_Tickets(dados.get(i).id_ticket, dados.get(i).desc_ticket, dados.get(i).status_ticket, dados.get(i).data_ticket,dados.get(i).inquilino_ticket)
            resultado.add(usuario)
        }
        return resultado
    }

    inner class ListaTicketgerAdapter : BaseAdapter {

        var Listatodostickets: ArrayList<Cad_Tickets>
        private var context: Context? = null

        constructor(context: Context, resultado: ArrayList<Cad_Tickets>) : super() {
            this.Listatodostickets  = resultado
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

            viewHolder.idticket?.text ="ID CTICKET: " + Listatodostickets[position].id_ticket
            viewHolder.inquilinonometicket?.text = "Inquilino: " + Listatodostickets[position].inquilino_ticket
            viewHolder.dataticket?.text = "Data: " + Listatodostickets[position].data_ticket
            viewHolder.statusticket?.text = "Status: " + Listatodostickets[position].status_ticket



            return view
        }


        override fun getItem(position: Int): Cad_Tickets{
            return Listatodostickets[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return Listatodostickets.size
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

    private fun displayInputDialog(pos: Int) {
        editatodostickets = Dialog(this)
        editatodostickets!!.setContentView(R.layout.edita_ticket)

        val ideditaticket: TextView = editatodostickets!!.findViewById(R.id.idticketedita)
        val inquilinoticket: TextView = editatodostickets!!.findViewById(R.id.nomeinquilinoticketedita)
        val descticket: EditText= editatodostickets!!.findViewById(R.id.descticketedita)
        val dataticket: TextView = editatodostickets!!.findViewById(R.id.dataticketedita)
        val statusticket: TextView = editatodostickets!!.findViewById(R.id.statusticketedita)
        val cancelabtn: Button = editatodostickets!!.findViewById(R.id.btncancelaticket)
        val atualizabtn: Button = editatodostickets!!.findViewById(R.id.btnfinalizaticket)
        val finalizaticket: RadioButton = editatodostickets!!.findViewById(R.id.rbfinalizaticket)


        if (pos == -1) {
            atualizabtn.isEnabled = false
            cancelabtn.isEnabled = false
        } else {
            atualizabtn.isEnabled = true
            cancelabtn.isEnabled = true
            ideditaticket.setText("ID CONTA: " + resultado[pos].id_ticket)
            inquilinoticket.setText("Locatário: " + resultado[pos].inquilino_ticket)
            descticket.setText(resultado[pos].desc_ticket)
            dataticket.setText("Data: " + resultado[pos].data_ticket)
            statusticket.setText("Status: " + resultado[pos].status_ticket)



        }

        atualizabtn.setOnClickListener {
            var novostatus = ""
            val idticket = resultado[pos].id_ticket
            if (finalizaticket.isChecked == true) {
                novostatus = "FECHADO"
            }

            if(novostatus == ""){
                Toast.makeText(this,"Favor Selecionar um status para Conta", Toast.LENGTH_SHORT).show()
            }else {
                val msgBox = AlertDialog.Builder(this)
                msgBox.setTitle("Finaliza Ticket")
                msgBox.setIcon(R.drawable.casarepara64b)
                msgBox.setMessage("Você deseja Finalizar o Ticket?")


                msgBox.setPositiveButton("SIM", { dialogInterface: DialogInterface, i: Int ->
                    db.atualizadadospend(idticket.toString(), novostatus)
                    editatodostickets?.dismiss()
                    finish()
                    val telaticketpend = Intent(this, MainActivityRelaTiketsAbertos::class.java)
                    startActivity(telaticketpend)
                    msgBox.setCancelable(true)
                    Toast.makeText(this, "Dados atualizados com Sucesso!!", Toast.LENGTH_LONG).show()

                })
                msgBox.setNegativeButton("NÃO", { dialogInterface: DialogInterface, i: Int ->
                    msgBox.setCancelable(true)
                    editatodostickets?.dismiss()
                })
                msgBox.show()
            }

        }

        cancelabtn.setOnClickListener {

                editatodostickets?.dismiss()
        }

        editatodostickets!!.show()
    }

}
