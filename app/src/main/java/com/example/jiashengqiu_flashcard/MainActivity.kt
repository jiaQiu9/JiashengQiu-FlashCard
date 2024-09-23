package com.example.jiashengqiu_flashcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.material3.Button
import androidx.compose.material3.Card


import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import com.example.jiashengqiu_flashcard.ui.theme.JiashengQiuFlashCardTheme
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JiashengQiuFlashCardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                    //Spacer(Modifier.size(30.dp))
                    Column(modifier= Modifier.padding(innerPadding)
                        .fillMaxWidth()){
                        Spacer(Modifier.size(30.dp))
                        FlashCard()

                    }
                }
            }
        }
    }
}

@Composable
fun FlashCard(){
    var text by remember{ mutableStateOf("")}
    var qA = remember{ mutableStateListOf <Pair<String,String>>(
        Pair("What is 1+1?","2"),
        Pair("What is the Abbreviation of the United Nations?(Enter in the format similar to U.S.A.)","U.N."),
        Pair("What is the abbreviation of Boston University?(Enter in the format similar to U.S.A.)","B.U."),
        Pair("What is the abbreviation of Boston College?(Enter in the format similar to U.S.A.)","B.C.")
    )}
    var currentIndex by remember{ mutableIntStateOf(0) }
    var correctness by remember{ mutableStateOf(false)}
    var (snackbarVisibleState, setSnackBarState) = remember { mutableStateOf(false) }
    var restartNot by remember{ mutableStateOf(false)}
    var buttonT by remember{mutableStateOf("")}

    Row(
        modifier = Modifier.fillMaxWidth()
    ){
        Card(
            modifier = Modifier
                .size(width = 250.dp, height = 100.dp)

        ){

            Text("${qA[currentIndex].first}")
        }
    }
    Spacer(Modifier.size(30.dp))
    Row( modifier = Modifier.fillMaxWidth()){
        TextField(
            value = text,
            onValueChange = {text=it},
            label= {Text("Answer")}
        )

    }

    Row(modifier = Modifier.fillMaxWidth()){
        Button(onClick = {

            if (qA[currentIndex].second == text && !snackbarVisibleState){
                text =""

                correctness=true

                // call snackbar
                // if current last index in the
                if (currentIndex<qA.size-1){
                    currentIndex++
                }
                else{
                    restartNot = true
                    //setSnackBarState(!snackbarVisibleState)
                }
                setSnackBarState(!snackbarVisibleState)
            }
            else if (qA[currentIndex].second != text && !snackbarVisibleState){
                text = ""
                correctness = false
                if(currentIndex <qA.size-1){
                    currentIndex++

                }else{
                    // call snack bar to see if the player wants to restart
                    restartNot=true
                    //setSnackBarState(!snackbarVisibleState)
                }
                setSnackBarState(!snackbarVisibleState)
            }
            else{
                buttonT="Please press to Continue"
            }
        }){
            Text("Submit ${buttonT}")
        }
    }

    if (snackbarVisibleState) {
        if (!restartNot){
            Snackbar(
                action = {
                    Button(onClick = {
                        setSnackBarState(!snackbarVisibleState)
                    }) {
                        Text("Close")
                    }
                },
                modifier = Modifier.padding(8.dp)

            ) {
                if (correctness){
                    Text(text="Correct.")
                }else{
                    Text(text="Incorrect.")
                }
                //Text(text = "Please enter a numeric value in the second field!!!")

            }
        }
        else if (restartNot){
            Snackbar(
                action = {
                    Row(){
                        Button(onClick = {
                            currentIndex =0
                            setSnackBarState(!snackbarVisibleState)
                        }) {
                            Text("Restart")
                        }
                        Spacer(modifier=Modifier.width(3.dp))
                        Button(onClick = {

                            setSnackBarState(!snackbarVisibleState)
                            buttonT=""
                        }) {
                            Text("Close")
                        }
                    }
                },
                modifier = Modifier.padding(8.dp)
            ){
                Text("Do you want to restart?")
            }
        }


    }

}
