package br.senai.sp.jandira.lionschool.gui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.R
import br.senai.sp.jandira.lionschool.model.CoursesList
import br.senai.sp.jandira.lionschool.model.Student
import br.senai.sp.jandira.lionschool.model.StudentList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TurmaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val siglaCurso = intent.getStringExtra("siglaCurso")
                    val nomeCurso = intent.getStringExtra("nomeCurso")

                    Greeting2(siglaCurso, nomeCurso)
                }
            }
        }
    }
}

@Composable
fun Greeting2(siglaCurso: String?, nomeCurso: String?) {

    val context = LocalContext.current

    val siglaCurso = siglaCurso!!
    val nomeCurso = nomeCurso!!


    val callAlunos = RetrofitFactory().getAlunosService().getCurso(siglaCurso)

    var alunos by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Student>())
    }

    //Call Alunos
    callAlunos.enqueue(object : Callback<StudentList> {
        override fun onResponse(
            call: Call<StudentList>,
            response: Response<StudentList>
        ) {
            //Duas exclamações seignificam que pode vir nulo
            alunos = response.body()!!.alunos;
        }

        override fun onFailure(call: Call<StudentList>, t: Throwable) {
            Log.i(
                "ds2m",
                "onFailure: ${t.message}"
            )
        }
    })


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0,0,0))

    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(Color( 140, 82, 255))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.voltar),
                    contentDescription = null
                )
            }

            Text(
                text = "Turma",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Image(
                painter = painterResource(id = R.drawable.logooo), contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(35.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        ) {

            Text(
                text = nomeCurso,
                fontSize = 42.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(35.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp), horizontalArrangement = Arrangement.SpaceAround
        ) {

            Card(
                modifier = Modifier
                    .height(25.dp)
                    .width(85.dp),
                shape = CircleShape
            ) {
                Text(
                    text = "Todos",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Card(
                modifier = Modifier
                    .height(25.dp)
                    .width(85.dp),
                shape = CircleShape
            ) {
                Text(
                    text = "Cursando",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Card(
                modifier = Modifier
                    .height(25.dp)
                    .width(85.dp),
                shape = CircleShape
            ) {
                Text(
                    text = "Finalizados",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

        }

        Spacer(modifier = Modifier.height(40.dp))


        LazyColumn() {
            items(alunos) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(horizontal = 35.dp)
                        .padding(top = 15.dp)
                        .clickable {
                            val intent = Intent(context, AlunoActivity::class.java)
                            context.startActivity(intent)
                            intent.putExtra("matricula", it.matricula)
                            Log.i("foto", "onFailure: ${it.foto}")
                            Log.i("nome", "onFailure: ${it.nome}")

                        },

                    shape = RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp,
                        bottomStart = 30.dp,
                        bottomEnd = 30.dp
                    ),
                    backgroundColor = Color(140, 82, 255),
                    border = BorderStroke(3.dp, Color.Black)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 30.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            shape = CircleShape

                        ) {

                            AsyncImage(model = it.foto, contentDescription = "Aluno",
                            modifier = Modifier.size(100.dp))

                        }


                        Log.i("Alunos", "${it.nome}: ")

                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .padding(vertical = 20.dp, horizontal = 20.dp),
                            verticalArrangement = Arrangement.SpaceBetween,

                            ) {

                            val cor =  if (it.status == "Cursando") {
                                Color(252, 191, 64)
                            } else {
                                Color(51, 71, 176)
                            }

                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "${it.nome}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Center

                            )

                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "${it.status}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )


                        }

                    }
                }


            }

        }
    }
}




private fun <T> Call<T>.enqueue(callback: Callback<StudentList>) {

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    LionSchoolTheme {
        Greeting2("Android", "")
    }
}