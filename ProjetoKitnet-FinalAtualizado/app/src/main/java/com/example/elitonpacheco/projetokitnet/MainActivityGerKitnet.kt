package com.example.elitonpacheco.projetokitnet


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.elitonpacheco.projetokitnet.R.id.lstGerkitnet
import kotlinx.android.synthetic.main.activity_main__kitnet.*
import kotlinx.android.synthetic.main.app_bar_main_activity__kitnet.*
import kotlinx.android.synthetic.main.lista_kitnet.*
import kotlinx.android.synthetic.main.nav_header_main_activity__kitnet.view.*

class MainActivityGerKitnet : AppCompatActivity() {
    private lateinit var speditalstinquilino:Spinner
    val context = this
    var listakitnet: ListView? = null
    var listaadapterkitnet: ListaKitnetAdapter? = null
    var dbkitnet = DataBaseHandlerKitnet(context)
    var resultado = ArrayList<ListaKitnet>()
    var adapter: ArrayAdapter<ListaKitnet>? = null
    var editakitnet: Dialog? = null
    var posicao = 0
    var resultadotexto = ArrayList<String>()
    var resultadonomes =  ArrayList<Get_Inquilinos>()
    var dbinquilino  = DataBaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ger_kitnet)



        resultado = generateData()
        resultadonomes = pegaNomesInquilinos()

        listakitnet = findViewById<ListView>(R.id.lstGerkitnet)
        listaadapterkitnet = ListaKitnetAdapter(this,resultado)
        listakitnet?.adapter = listaadapterkitnet
        editakitnet = Dialog(this)
        editakitnet!!.setContentView(R.layout.edita_kitnet)
        listakitnet?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            //Toast.makeText(this, "Click on " + resultado[position].nome_inquilino, Toast.LENGTH_SHORT).show() Foi usado para identificar a posição selecionada
            if (editakitnet != null) {
                if (!editakitnet!!.isShowing) {
                    displayInputDialog(position)
                    posicao = position
                } else {
                    editakitnet!!.dismiss()
                }
            }
        }
        listaadapterkitnet?.notifyDataSetChanged()
    }

    private fun pegaNomesInquilinos(): ArrayList<Get_Inquilinos> {
        var resultadokitnet = ArrayList<Get_Inquilinos>()
        var dadoskitnet = dbinquilino.consultaInquilinos()

        for (i in 0..(dadoskitnet.size - 1)) {
            var apkit = Get_Inquilinos(dadoskitnet.get(i).nome_inquilino)

            resultadokitnet.add(apkit)
        }
        return resultadokitnet
    }

        private fun generateData(): ArrayList<ListaKitnet> {
        var resultadokitnet = ArrayList<ListaKitnet>()
        var dadoskitnet = dbkitnet.consultaDados()

        for(i in 0..(dadoskitnet.size-1)){
            var apkit = ListaKitnet(dadoskitnet.get(i).id_kitnet.toString(),dadoskitnet.get(i).nome_kitnet,dadoskitnet.get(i).qtd_quartos,dadoskitnet.get(i).num_kitnet,dadoskitnet.get(i).status_kitnet,dadoskitnet.get(i).inquilino_kitnet)
            resultadokitnet.add(apkit)
        }
        return resultadokitnet
    }

    inner class ListaKitnetAdapter : BaseAdapter {

        var ListaKitnet: ArrayList<ListaKitnet>
        private var context: Context? = null

        constructor(context: Context, resultado: ArrayList<ListaKitnet>) : super() {
            this.ListaKitnet = resultado
            this.context = context
        }


        @RequiresApi(Build.VERSION_CODES.O)
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view: View?
            val viewHolder: ViewHolder

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.lista_kitnet, null)
                viewHolder = ViewHolder(view)
                view?.tag = viewHolder
                Log.i("JSA", "set Tag for ViewHolder, position: " + position)
            } else {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }

            viewHolder.idkitnet?.text = "ID Kitnet: " + ListaKitnet[position].id_kitnet
            viewHolder.nomekitnet?.text = "Nome: " + ListaKitnet[position].nome_kitnet
            viewHolder.qtdquartos?.text = ListaKitnet[position].qtd_quartos + " Quarto(s)"
            viewHolder.numkitnet?.text = "N°: " + ListaKitnet[position].num_kitnet
            viewHolder.statuskitnet?.text = "Status: " + ListaKitnet[position].status_kitnet
            viewHolder.inquilinokitnet?.text = "Locatario: " + ListaKitnet[position].inquilino_kitnet


            return view
        }


        override fun getItem(position: Int): ListaKitnet {
            return ListaKitnet[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return ListaKitnet.size
        }
    }

    private class ViewHolder(row: View?) {
        var idkitnet: TextView? = null
        var nomekitnet: TextView? = null
        var qtdquartos: TextView? = null
        var numkitnet: TextView? = null
        var statuskitnet: TextView? = null
        var inquilinokitnet: TextView? = null

        init {
            this.idkitnet = row?.findViewById<TextView>(R.id.txtidkitnet)
            this.nomekitnet = row?.findViewById<TextView>(R.id.txtnomekitnet)
            this.qtdquartos = row?.findViewById<TextView>(R.id.txtqtdquartos)
            this.numkitnet = row?.findViewById<TextView>(R.id.txtnumkitnet)
            this.statuskitnet = row?.findViewById<TextView>(R.id.txtstatuskitnet)
            this.inquilinokitnet = row?.findViewById<TextView>(R.id.txtinquilinokitnet)
        }
    }

    fun update(position: Int, newName: ListaKitnet): Boolean? {
        try {
            resultado.removeAt(position)
            resultado.add(position, newName)

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

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

    @SuppressLint("ResourceType")
    private fun displayInputDialog(pos: Int) {
        editakitnet = Dialog(this)
        editakitnet!!.setContentView(R.layout.edita_kitnet)

        speditalstinquilino = editakitnet!!.findViewById<Spinner>(R.id.speditainquilinolocado)
        resultadotexto.add("LIVRE")
        for(i in 0..(resultadonomes.size -1)){
            resultadotexto.add(resultadonomes[i].nome_inquilino)
        }

        val ideditakitnet: TextView = editakitnet!!.findViewById(R.id.lblideditakitnet)
        val nomekitnet: EditText = editakitnet!!.findViewById(R.id.txteditanomekitnet)
        val qtdquarto: EditText = editakitnet!!.findViewById(R.id.txteditaqtdquartos)
        val numkitnet: EditText = editakitnet!!.findViewById(R.id.txteditanumkitnet)
        val statuslivrekitnet: RadioButton = editakitnet!!.findViewById(R.id.rblivrestatuskitnet)
        val statusocupadokitnet: RadioButton = editakitnet!!.findViewById(R.id.rbocupadostatuskitnet)
        val statusreservadokitnet: RadioButton = editakitnet!!.findViewById(R.id.rbreservadostatuskitnet)
        val inquilinolocado: Spinner = editakitnet!!.findViewById(R.id.speditainquilinolocado)
        val atualizabtn: Button = editakitnet!!.findViewById(R.id.btnatualizakitnet)
        val deletabtn: Button = editakitnet!!.findViewById(R.id.btndeletakitnet)
        var peganomelocatario = ""

        val adapterinquilino = ArrayAdapter(this,android.R.layout.simple_spinner_item,resultadotexto)
        adapterinquilino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        speditalstinquilino.adapter = adapterinquilino

        if (pos == -1) {
            atualizabtn.isEnabled = false
            deletabtn.isEnabled = false
        } else {
            atualizabtn.isEnabled = true
            deletabtn.isEnabled = true
            ideditakitnet.setText("ID Inquilino: " + resultado[pos].id_kitnet)
            nomekitnet.setText(resultado[pos].nome_kitnet)
            qtdquarto.setText(resultado[pos].qtd_quartos)
            numkitnet.setText(resultado[pos].num_kitnet)
            peganomelocatario = resultado[pos].inquilino_kitnet

            for(i in 0..(resultadotexto.size -1)){
                if(peganomelocatario == resultadotexto[i]){
                    inquilinolocado.setSelection(i)

                }
            }

            if(resultado[pos].status_kitnet == "LIVRE"){
                statuslivrekitnet.isChecked = true
            }
            if(resultado[pos].status_kitnet == "OCUPADO"){
                statusocupadokitnet.isChecked = true
            }
            if(resultado[pos].status_kitnet == "RESERVADO"){
                statusreservadokitnet.isChecked = true
            }

        }

        atualizabtn.setOnClickListener {
            val novonomekitnet = nomekitnet.text
            val novoqtdquartos = qtdquarto.text
            val novonumkitnet = numkitnet.text
            val novoinquilino = inquilinolocado.selectedItem.toString()
            var novostatuskitnet = ""


                if(statuslivrekitnet.isChecked == true){
                    novostatuskitnet = "LIVRE"
                }
                if(statusocupadokitnet.isChecked == true){
                    novostatuskitnet = "OCUPADO"
                }
                if(statusreservadokitnet.isChecked == true){
                    novostatuskitnet = "RESERVADO"
                }
            val idkitnet = resultado[pos].id_kitnet

            if (novonomekitnet.length > 0 && novoqtdquartos.length > 0 && novonumkitnet.length > 0 ) {

                val msgBox = AlertDialog.Builder(this)
                msgBox.setTitle("Atualizar Dados da Kitnet")
                msgBox.setIcon(R.drawable.atualizacasa64b)
                msgBox.setMessage("Você deseja realmente atualizar os dados da Kitnet?")


                msgBox.setPositiveButton("SIM", { dialogInterface: DialogInterface, i: Int ->
                    dbkitnet.atualizaDadoskitnet(idkitnet, novonomekitnet.toString(), novoqtdquartos.toString(),novonumkitnet.toString(),novoinquilino,novostatuskitnet)
                    editakitnet?.dismiss()
                    finish()
                    val telagerinqui = Intent(this, MainActivityGerKitnet::class.java)
                    startActivity(telagerinqui)
                    msgBox.setCancelable(true)
                    Toast.makeText(this, "Dados atualizados com Sucesso!!", Toast.LENGTH_LONG).show()

                })
                msgBox.setNegativeButton("NÃO", { dialogInterface: DialogInterface, i: Int ->
                    msgBox.setCancelable(true)
                    editakitnet?.dismiss()
                })
                msgBox.show()

            }
        }

        deletabtn.setOnClickListener {
            val msgBox = AlertDialog.Builder(this)
            msgBox.setTitle("Excluir Kitnet")
            msgBox.setIcon(R.drawable.deletacasa64b)
            msgBox.setMessage("Você deseja realmente excluir a Kitnet?")


            msgBox.setPositiveButton("SIM", { dialogInterface: DialogInterface, i: Int ->
                dbkitnet.deletaDados(resultado[pos].id_kitnet)
                delete(posicao)
                editakitnet?.dismiss()
                finish()
                val telagerinqui = Intent(this, MainActivityGerKitnet::class.java)
                startActivity(telagerinqui)
                Toast.makeText(this, "Inquilino excluido com sucesso!!", Toast.LENGTH_LONG).show()
                msgBox.setCancelable(true)

            })
            msgBox.setNegativeButton("NÃO", { dialogInterface: DialogInterface, i: Int ->
                msgBox.setCancelable(true)
                editakitnet?.dismiss()
            })
            msgBox.show()

        }

        editakitnet!!.show()
    }

}

