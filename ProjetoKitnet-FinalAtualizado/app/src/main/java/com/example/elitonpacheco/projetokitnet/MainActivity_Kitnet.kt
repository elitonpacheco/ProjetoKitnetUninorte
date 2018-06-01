package com.example.elitonpacheco.projetokitnet

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main__kitnet.*
import kotlinx.android.synthetic.main.activity_main_cad_kitnet.*
import kotlinx.android.synthetic.main.activity_main_ger_inquilino.*
import kotlinx.android.synthetic.main.app_bar_main_activity__kitnet.*
import kotlinx.android.synthetic.main.lista_contas.view.*
import kotlinx.android.synthetic.main.lista_ticket.view.*
import kotlinx.android.synthetic.main.nav_header_main_activity__kitnet.*


class MainActivity_Kitnet : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main__kitnet)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val it = intent

        val nomeinquilino = it.getStringExtra("nomeinquilino")
        val tipoinquilino = it.getStringExtra("tipoinquilino")


        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val txtUsuarioLogado: TextView = headerView.findViewById(R.id.txtinquilinonomemenu)

        if(nomeinquilino == null){
            txtUsuarioLogado.text = "Olá Usuário Master"
        }else{
            txtUsuarioLogado.text ="Olá " + nomeinquilino
        }








        if(tipoinquilino == "Usuario") {
                var nav = findViewById<NavigationView>(R.id.nav_view)
                var menu = nav.menu
                menu.findItem(R.id.cad_inquilino).isVisible = false
                menu.findItem(R.id.cad_kitnet).isVisible = false
                menu.findItem(R.id.edit_inquilino).isVisible = false
                menu.findItem(R.id.edit_kitnet).isVisible = false
                menu.findItem(R.id.gera_recibo).isVisible = false
                menu.findItem(R.id.gerencia_pagamentos).isVisible = false
            }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.gera_recibo -> {
                val telagerarecibo = Intent(this, MainActivityRecibo::class.java)
                startActivity(telagerarecibo)
            }
            R.id.cad_inquilino -> {
                val telacadinquilino = Intent(this, MainActivityCadInquilino::class.java)
                startActivity(telacadinquilino)
            }
            R.id.cad_kitnet -> {
                val telacadkitnet = Intent(this, MainActivityCadKitnet::class.java)
                startActivity(telacadkitnet)
            }
            R.id.edit_inquilino -> {
                val telagerinqui = Intent(this, MainActivityGerInquilino::class.java)
                startActivity(telagerinqui)

            }
            R.id.edit_kitnet -> {
                val telagerKitnet = Intent(this, MainActivityGerKitnet::class.java)
                startActivity(telagerKitnet)

            }
            R.id.gerencia_pagamentos -> {
                val telagerpagamentos = Intent(this, MainActivityGerPagamentos::class.java)
                startActivity(telagerpagamentos)
            }
            R.id.rela_pag_pendente -> {
                val telarelapagamentos = Intent(this, MainActivityPendsPagamentos::class.java)
                startActivity(telarelapagamentos)
            }
            R.id.hist_pagamentos -> {
                val telahistpagamentos = Intent(this, MainActivityFinHistorico::class.java)
                startActivity(telahistpagamentos)
            }
            R.id.manut_relatorio -> {
                val telarelamanutabertos = Intent(this, MainActivityRelaTiketsAbertos::class.java)
                startActivity(telarelamanutabertos)
            }
            R.id.gera_tiket_manut -> {
                val telageratiket = Intent(this, MainActivityGeraTiketManut::class.java)
                startActivity(telageratiket)
            }
            R.id.historio_Tikets -> {
                val telahisttiket = Intent(this, MainActivityHist_Tikets::class.java)
                startActivity(telahisttiket)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}
