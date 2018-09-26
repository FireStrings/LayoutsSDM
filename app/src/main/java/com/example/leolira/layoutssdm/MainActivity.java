package com.example.leolira.layoutssdm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String ESTADO_NOTIFICACAO_CHECKBOX = "ESTADO_NOTIFICACAO_CHECKBOX";
    private final String NOTIFICACAO_RADIOBUTTON_SELECIONADO = "NOTIFICACAO_RADIOBUTTON_SELECIONADO";

    private CheckBox notificacoesCheckBox;
    private RadioGroup notificacoesRadioGroup;
    private EditText nomeEditText;
    private EditText telefoneEditText;
    private EditText emailEditText;
    private LinearLayout telefoneLinearLayout;
    private ArrayList<View> telefoneArrayList;
    private LayoutInflater layoutInflater;
    private LinearLayout emailLinearLayout;
    private ArrayList<View> emailArrayList;
    private HashMap<String, Integer> telefoneList;
    private HashMap<String, Integer> emailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view_activity_main);

        this.initComponentes();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ESTADO_NOTIFICACAO_CHECKBOX, this.notificacoesCheckBox.isChecked());
        outState.putInt(NOTIFICACAO_RADIOBUTTON_SELECIONADO, this.notificacoesRadioGroup.getCheckedRadioButtonId());

        this.emailList.clear();
        for (View v : this.emailArrayList)
        {
            EditText innerEmailEditText = v.findViewById(R.id.innerEmailEditText);
            Spinner innerTipoEmailSpinner = v.findViewById(R.id.innerTipoEmailSpinner);
            this.emailList.put(innerEmailEditText.getText().toString(),innerTipoEmailSpinner.getSelectedItemPosition());
        }
        outState.putSerializable("LISTA_EMAILS",emailList);

        this.telefoneList.clear();
        for (View v : telefoneArrayList)
        {
            EditText innerTelefoneEditText = v.findViewById(R.id.innerTelefoneEditText);
            Spinner innerTipoTelefoneSpinner = v.findViewById(R.id.innerTipoTelefoneSpinner);
            this.telefoneList.put(innerTelefoneEditText.getText().toString(),innerTipoTelefoneSpinner.getSelectedItemPosition());
        }
        outState.putSerializable("LISTA_TELEFONES",telefoneList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        this.notificacoesCheckBox.setChecked(savedInstanceState.getBoolean(ESTADO_NOTIFICACAO_CHECKBOX, false));
        int idRadioButtonSelecionado = savedInstanceState.getInt(NOTIFICACAO_RADIOBUTTON_SELECIONADO, -1);

        if (idRadioButtonSelecionado != -1) {
            this.notificacoesRadioGroup.check(idRadioButtonSelecionado);
        }

        this.emailList = (HashMap<String,Integer>)savedInstanceState.getSerializable("LISTA_EMAILS");
        for (Map.Entry<String, Integer> v : this.emailList.entrySet())
        {
            View novoEmailLayout = this.layoutInflater.inflate(R.layout.novo_email_layout,null);
            ((EditText)novoEmailLayout.findViewById(R.id.innerEmailEditText)).setText(v.getKey());
            ((Spinner)novoEmailLayout.findViewById(R.id.innerTipoEmailSpinner)).setSelection(v.getValue());
            this.emailArrayList.add(novoEmailLayout);
            this.emailLinearLayout.addView(novoEmailLayout);

        }

        this.telefoneList = (HashMap<String, Integer>)savedInstanceState.getSerializable("LISTA_TELEFONES");
        for (Map.Entry<String, Integer> v : this.telefoneList.entrySet())
        {
            View novoTelefoneLayout = this.layoutInflater.inflate(R.layout.novo_telefone_layout,null);
            ((EditText)novoTelefoneLayout.findViewById(R.id.innerTelefoneEditText)).setText(v.getKey());
            ((Spinner)novoTelefoneLayout.findViewById(R.id.innerTipoTelefoneSpinner)).setSelection(v.getValue());
            this.telefoneArrayList.add(novoTelefoneLayout);
            this.telefoneLinearLayout.addView(novoTelefoneLayout);

        }
    }

    public void adicionarEmail(View botao){
        if (botao.getId() == R.id.adicionarEmailButton){
            View novoEmailLayout = this.layoutInflater.inflate(R.layout.novo_email_layout,null);
            this.emailArrayList.add(novoEmailLayout);
            this.emailLinearLayout.addView(novoEmailLayout);
        }
    }

    public void adicionarTelefone(View botao){
        if (botao.getId() == R.id.adicionarTelefoneButton){
            View novoTelefoneLayout = this.layoutInflater.inflate(R.layout.novo_telefone_layout,null);
            this.telefoneArrayList.add(novoTelefoneLayout);
            this.telefoneLinearLayout.addView(novoTelefoneLayout);
        }
    }

    public void limparFormulario(View botao){
        this.emailEditText.setText("");
        this.telefoneArrayList.clear();
        this.telefoneLinearLayout.removeAllViews();
        this.emailArrayList.clear();
        this.emailLinearLayout.removeAllViews();
        this.nomeEditText.requestFocus();
        this.notificacoesCheckBox.setChecked(false);
        this.notificacoesRadioGroup.clearCheck();
        this.nomeEditText.setText("");
        this.telefoneEditText.setText("");
    }

    private void initComponentes(){
        this.notificacoesCheckBox = findViewById(R.id.notificacoesCheckBox);
        this.notificacoesRadioGroup = findViewById(R.id.notificacoesRadioGroup);
        this.nomeEditText = findViewById(R.id.nomeEditText);
        this.telefoneEditText = findViewById(R.id.telefoneEditText);
        this.emailEditText = findViewById(R.id.emailEditText);
        this.telefoneLinearLayout = findViewById(R.id.telefoneLinearLayout);
        this.telefoneArrayList = new ArrayList<>();
        this.emailLinearLayout = findViewById(R.id.emailLinearLayout);
        this.emailArrayList = new ArrayList<>();

        if (this.telefoneList == null) {
            this.telefoneList = new HashMap<>();
        }
        if (this.emailList == null) {
            this.emailList = new HashMap<>();
        }
        this.layoutInflater = getLayoutInflater();

        this.notificacoesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    notificacoesRadioGroup.setVisibility(View.VISIBLE);
                }
                else
                {
                    notificacoesRadioGroup.setVisibility(View.GONE);
                }
            }
        });
    }
}
