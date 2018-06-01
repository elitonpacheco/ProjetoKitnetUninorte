package com.example.elitonpacheco.projetokitnet

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.elitonpacheco.projetokitnet.R.id.*
import kotlinx.android.synthetic.main.activity_main_cad_inquilino.*

class MainActivityCadInquilino : AppCompatActivity() {
    var contador = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_cad_inquilino)


        txtCpf.addTextChangedListener(Mask.mask("###.###.###-##", txtCpf))
        txtFone.addTextChangedListener(Mask.mask("(##) #####-####", txtFone))


        }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            menuInflater.inflate(R.menu.menu_cad_inquilino, menu)
            return true
        }

        @RequiresApi(Build.VERSION_CODES.O)

        override fun onOptionsItemSelected(item: MenuItem?): Boolean {

            var id = item!!.itemId
            val context = this
            var radiousuario = findViewById<RadioButton>(R.id.rbUsuario)
            var radioadmin = findViewById<RadioButton>(R.id.rbAdmin)
            var tipoUsuario = ""
            var status = "Ativo"
            var passwordconfirma = ""
            var errosenha = 0

            if (id == btn_cadastra_inquilino){
                if(txtNomeInquilino.text.toString().length == 0 || txtEmail.text.toString().length == 0 || txtCpf.text.toString().length == 0 || txtFone.text.toString().length == 0 || txtPassword.text.toString().length == 0 || txtPasswordConfirma.text.toString().length == 0) {
                    Toast.makeText(context, "Todos os Campos São Obrigatórios!!", Toast.LENGTH_LONG).show()
                }
                    if(txtPassword.text.toString() != txtPasswordConfirma.text.toString() || txtPassword.text.toString() == "" && txtPasswordConfirma.text.toString() == "" ){
                        Toast.makeText(context,"As Senhas Digitadas não Coincidem ou estão em branco!!",Toast.LENGTH_LONG).show()
                        txtPassword.setText("")
                        txtPasswordConfirma.setText("")
                        txtPassword.requestFocus()

                    }else{
                            errosenha = 1
                            passwordconfirma = txtPasswordConfirma.text.toString()
                    }

                            if(radiousuario?.isChecked == true){
                                tipoUsuario = rbUsuario.text.toString()
                            }
                            if(radioadmin?.isChecked == true){
                                tipoUsuario = rbAdmin.text.toString()
                            }


                if(errosenha == 1) {
                    var inquilino = Cad_Inquilino(txtNomeInquilino.text.toString(), txtEmail.text.toString(), txtCpf.text.toString(), txtFone.text.toString(), passwordconfirma, status, tipoUsuario)
                    var db = DataBaseHandler(context)
                    db.inserirDados(inquilino)
                    txtNomeInquilino.setText("")
                    txtEmail.setText("")
                    txtCpf.setText("")
                    txtFone.setText("")
                    txtPassword.setText("")
                    txtPasswordConfirma.setText("")
                    rdGrupoTipo.clearCheck()
                    txtNomeInquilino.requestFocus()
                    if(contador < 1) {
                        val msgBox = AlertDialog.Builder(this)
                        msgBox.setTitle("Cadastrar Inquilino")
                        msgBox.setIcon(R.drawable.inquilino64)
                        msgBox.setMessage("Você deseja cadastrar outro inquilino?")
                        msgBox.setPositiveButton("SIM", { dialogInterface: DialogInterface, i: Int ->
                            contador += 1
                            msgBox.setCancelable(true)
                        })
                        msgBox.setNegativeButton("NÃO", { dialogInterface: DialogInterface, i: Int ->
                            msgBox.setCancelable(true)
                            finish()

                        })
                        msgBox.show()
                    }
                }
                return true
            }
            return super.onOptionsItemSelected(item)

        }

}
