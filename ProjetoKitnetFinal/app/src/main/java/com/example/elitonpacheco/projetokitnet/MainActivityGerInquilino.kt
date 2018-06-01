package com.example.elitonpacheco.projetokitnet

import android.app.AlertDialog
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.elitonpacheco.projetokitnet.R.layout.lista_inquilino


class MainActivityGerInquilino : AppCompatActivity() {
    var context: Context? = null
    var lista: ListView? = null
    var listaadapter: ListaInquilinoAdapter? = null
    var db = DataBaseHandler(this)
    var resultado = ArrayList<ListaInquilinos>()
    var adapter: ArrayAdapter<ListaInquilinos>? = null
    var editainquilino: Dialog? = null
    var posicao = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ger_inquilino)

        resultado = generateData()
        lista = findViewById<ListView>(R.id.lstInquilinos)
        listaadapter = ListaInquilinoAdapter(this, resultado)

        lista?.adapter = listaadapter
        editainquilino = Dialog(this)
        editainquilino!!.setContentView(R.layout.edita_inquilino)
        lista?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            //Toast.makeText(this, "Click on " + resultado[position].nome_inquilino, Toast.LENGTH_SHORT).show() Foi usado para identificar a posição selecionada
            if (editainquilino != null) {
                if (!editainquilino!!.isShowing) {
                    displayInputDialog(position)
                    posicao = position
                } else {
                    editainquilino!!.dismiss()
                }
            }
        }
        listaadapter?.notifyDataSetChanged()


    }


    private fun generateData(): ArrayList<ListaInquilinos> {

        var dados = db.consultaDados()

        for (i in 0..(dados.size - 1)) {
            var usuario = ListaInquilinos(dados.get(i).id_inquilino.toString(), dados.get(i).nome_inquilino, dados.get(i).email_inquilino, dados.get(i).cpf_inquilino, dados.get(i).celular_inquilino, dados.get(i).tipo_inquilino)
            resultado.add(usuario)
        }
        return resultado
    }

    inner class ListaInquilinoAdapter : BaseAdapter {

        var ListaInquilinos: ArrayList<ListaInquilinos>
        private var context: Context? = null

        constructor(context: Context, resultado: ArrayList<ListaInquilinos>) : super() {
            this.ListaInquilinos = resultado
            this.context = context
        }


        @RequiresApi(Build.VERSION_CODES.O)
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view: View?
            val viewHolder: ViewHolder

            if (convertView == null) {
                view = layoutInflater.inflate(lista_inquilino, null)
                viewHolder = ViewHolder(view)
                view?.tag = viewHolder
                Log.i("JSA", "set Tag for ViewHolder, position: " + position)
            } else {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }

            viewHolder.idInquilino?.text = ListaInquilinos[position].id_inquilino
            viewHolder.nomeInquilino?.text = ListaInquilinos[position].nome_inquilino
            viewHolder.emailInquilino?.text = ListaInquilinos[position].email_inquilino
            viewHolder.cpfInquilino?.text = ListaInquilinos[position].cpf_inquilino
            viewHolder.celularInquilino?.text = ListaInquilinos[position].celular_inquilino


            return view
        }


        override fun getItem(position: Int): ListaInquilinos {
            return ListaInquilinos[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return ListaInquilinos.size
        }
    }

    private inner class ViewHolder(row: View?) {
        var idInquilino: TextView? = null
        var nomeInquilino: TextView? = null
        var emailInquilino: TextView? = null
        var cpfInquilino: TextView? = null
        var celularInquilino: TextView? = null
        var edita: ImageButton? = null
        var deleta: ImageButton? = null


        init {
            this.idInquilino = row?.findViewById<TextView>(R.id.txtIdInquilino)
            this.nomeInquilino = row?.findViewById<TextView>(R.id.txtNomeInquilino)
            this.emailInquilino = row?.findViewById<TextView>(R.id.txtEmailInquilino)
            this.cpfInquilino = row?.findViewById<TextView>(R.id.txtCpfInquilino)
            this.celularInquilino = row?.findViewById<TextView>(R.id.txtCelularInquilino)
        }
    }

    fun update(position: Int, newName: ListaInquilinos): Boolean? {
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

    private fun displayInputDialog(pos: Int) {
        editainquilino = Dialog(this)
        editainquilino!!.setTitle("LISTVIEW CRUD")
        editainquilino!!.setContentView(R.layout.edita_inquilino)

        val ideditainquilino: TextView = editainquilino!!.findViewById(R.id.lblidinquilino)
        val emailedita: EditText = editainquilino!!.findViewById(R.id.txtemailedita)
        val celularedita: EditText = editainquilino!!.findViewById(R.id.txtcelularedita)
        val atualizabtn: Button = editainquilino!!.findViewById(R.id.btnatualiza)
        val deletabtn: Button = editainquilino!!.findViewById(R.id.btndeleta)
        celularedita.addTextChangedListener(Mask.mask("(##) #####-####", celularedita))
        if (pos == -1) {
            atualizabtn.isEnabled = false
            deletabtn.isEnabled = false
        } else {
            atualizabtn.isEnabled = true
            deletabtn.isEnabled = true
            ideditainquilino.setText("ID Inquilino: " + resultado[pos].id_inquilino)
            emailedita.setText(resultado[pos].email_inquilino)
            celularedita.setText(resultado[pos].celular_inquilino)
        }

        atualizabtn.setOnClickListener {
            val novoemail = emailedita.text
            val novocelular = celularedita.text
            val id_inquilino = resultado[pos].id_inquilino

            if (novoemail.length > 0 && novocelular.length == 15 && novoemail != null && novocelular != null) {

                val msgBox = AlertDialog.Builder(this)
                msgBox.setTitle("Atualizar Dados do Inquilino")
                msgBox.setIcon(R.drawable.atualizauser64)
                msgBox.setMessage("Você deseja realmente atualizar os dados do inquilino?")


                msgBox.setPositiveButton("SIM", { dialogInterface: DialogInterface, i: Int ->
                    db.atualizadados(id_inquilino, novoemail.toString(), novocelular.toString())
                    editainquilino?.dismiss()
                    finish()
                    val telagerinqui = Intent(this, MainActivityGerInquilino::class.java)
                    startActivity(telagerinqui)
                    msgBox.setCancelable(true)
                    Toast.makeText(this, "Dados atualizados com Sucesso!!", Toast.LENGTH_LONG).show()

                })
                msgBox.setNegativeButton("NÃO", { dialogInterface: DialogInterface, i: Int ->
                    msgBox.setCancelable(true)
                    editainquilino?.dismiss()
                })
                msgBox.show()

            }
        }

        deletabtn.setOnClickListener {
            val msgBox = AlertDialog.Builder(this)
            msgBox.setTitle("Excluir Inquilino")
            msgBox.setIcon(R.drawable.icodeleta64)
            msgBox.setMessage("Você deseja realmente excluir do inquilino?")


            msgBox.setPositiveButton("SIM", { dialogInterface: DialogInterface, i: Int ->
                db.deletaDados(resultado[pos].id_inquilino)
                delete(posicao)
                editainquilino?.dismiss()
                finish()
                val telagerinqui = Intent(this, MainActivityGerInquilino::class.java)
                startActivity(telagerinqui)
                Toast.makeText(this, "Inquilino excluido com sucesso!!", Toast.LENGTH_LONG).show()
                msgBox.setCancelable(true)

            })
            msgBox.setNegativeButton("NÃO", { dialogInterface: DialogInterface, i: Int ->
                msgBox.setCancelable(true)
                editainquilino?.dismiss()
            })
            msgBox.show()

        }

        editainquilino!!.show()
    }



}



