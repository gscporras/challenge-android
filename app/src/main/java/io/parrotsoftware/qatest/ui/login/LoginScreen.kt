package io.parrotsoftware.qatest.ui.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import io.parrotsoftware.qatest.BuildConfig
import io.parrotsoftware.qatest.R
import io.parrotsoftware.qatest.extensions.showToast
import io.parrotsoftware.qatest.ui.custom.ParrotButton
import io.parrotsoftware.qatest.ui.custom.ParrotLoading
import io.parrotsoftware.qatest.ui.custom.ParrotOutlinedTextField
import io.parrotsoftware.qatest.ui.login.viewmodel.LoginViewModel
import io.parrotsoftware.qatest.ui.theme.white

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navigateToHome: () -> Unit
) {
    val viewState by viewModel.state.collectAsState()

    if(viewState.navigateToHome) {
        navigateToHome()
        viewState.navigateToHome = false
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LoginContent(
            errorMessage = viewState.errorMessage,
            isLoading = viewState.isLoading,
            viewModel = viewModel
        )
    }
}

@Composable
fun LoginContent(
    errorMessage: String? = "",
    isLoading: Boolean = false,
    viewModel: LoginViewModel
) {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val email = remember { mutableStateOf(viewModel.email.value) }
    val password = rememberSaveable { mutableStateOf(viewModel.password.value) }

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LoginLandscape(
                email,
                password,
                signIn = { viewModel.signIn(email.value ?: "", password.value ?: "") }
            )
        }
        else -> {
            LoginPortrait(
                email,
                password,
                signIn = { viewModel.signIn(email.value ?: "", password.value ?: "") }
            )
        }
    }

    if(!errorMessage.isNullOrEmpty()) {
        context.showToast(errorMessage)
    }

    if(isLoading) {
        ParrotLoading()
    }
}

@Composable
@Preview
fun LoginPortrait(
    email: MutableState<String?> = mutableStateOf(BuildConfig.EMAIL_TEST),
    password: MutableState<String?> = mutableStateOf(BuildConfig.PASSWORD_TEST),
    signIn: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val passwordVisibility = remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {

        val (banner, img1, img2, title, emailInput, passwordInput, button) = createRefs()

        Banner(
            modifier = Modifier
                .constrainAs(banner) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        Image(
            modifier = Modifier
                .constrainAs(img1) {
                    top.linkTo(banner.bottom)
                    end.linkTo(parent.end)
                },
            painter = painterResource(id = R.drawable.bg_login_corner_right),
            contentDescription = null
        )

        Image(
            modifier = Modifier
                .constrainAs(img2) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                },
            painter = painterResource(id = R.drawable.bg_login_corner_left),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(banner.bottom, 64.dp)
                    start.linkTo(parent.start, 32.dp)
                    end.linkTo(parent.end, 32.dp)
                    width = Dimension.fillToConstraints
                },
            text = stringResource(id = R.string.title_connect_here),
            fontSize = 24.sp,
            color = Color.Black,
            style = MaterialTheme.typography.h1
        )

        ParrotOutlinedTextField(
            modifier = Modifier
                .constrainAs(emailInput) {
                    top.linkTo(title.bottom, 16.dp)
                    start.linkTo(title.start)
                    end.linkTo(title.end)
                    width = Dimension.fillToConstraints
                },
            onNext = { focusRequester.requestFocus() },
            value = email
        )

        ParrotOutlinedTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .constrainAs(passwordInput) {
                    top.linkTo(emailInput.bottom, 16.dp)
                    start.linkTo(emailInput.start)
                    end.linkTo(emailInput.end)
                    width = Dimension.fillToConstraints
                },
            value = password,
            isPasswordTextField = true,
            onGo = { signIn() },
            passwordVisibility = passwordVisibility
        )

        ParrotButton(
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(passwordInput.bottom, 32.dp)
                    start.linkTo(title.start)
                },
            onClick = { signIn() }
        )
    }
}

@Composable
@Preview(device = Devices.AUTOMOTIVE_1024p)
fun LoginLandscape(
    email: MutableState<String?> = mutableStateOf(BuildConfig.EMAIL_TEST),
    password: MutableState<String?> = mutableStateOf(BuildConfig.PASSWORD_TEST),
    signIn: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val passwordVisibility = remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {

        val (banner, img1, img2, title, emailInput, passwordInput, button) = createRefs()

        Banner(
            modifier = Modifier
                .constrainAs(banner) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    height = Dimension.fillToConstraints
                }
                .fillMaxWidth(.3f)
        )

        Image(
            modifier = Modifier
                .constrainAs(img1) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            painter = painterResource(id = R.drawable.bg_login_corner_right),
            contentDescription = null
        )

        Image(
            modifier = Modifier
                .constrainAs(img2) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(banner.end)
                },
            painter = painterResource(id = R.drawable.bg_login_corner_left),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top, 64.dp)
                    start.linkTo(banner.end, 32.dp)
                    end.linkTo(parent.end, 32.dp)
                    width = Dimension.fillToConstraints
                },
            text = stringResource(id = R.string.title_connect_here),
            fontSize = 24.sp,
            color = Color.Black,
            style = MaterialTheme.typography.h1
        )

        ParrotOutlinedTextField(
            modifier = Modifier
                .constrainAs(emailInput) {
                    top.linkTo(title.bottom, 16.dp)
                    start.linkTo(title.start)
                    end.linkTo(title.end)
                    width = Dimension.fillToConstraints
                },
            value = email,
            onNext = { focusRequester.requestFocus() }
        )

        ParrotOutlinedTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .constrainAs(passwordInput) {
                    top.linkTo(emailInput.bottom, 16.dp)
                    start.linkTo(emailInput.start)
                    end.linkTo(emailInput.end)
                    width = Dimension.fillToConstraints
                },
            value = password,
            isPasswordTextField = true,
            onGo = { signIn() },
            passwordVisibility = passwordVisibility
        )

        ParrotButton(
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(passwordInput.bottom, 32.dp)
                    start.linkTo(title.start)
                },
            onClick = { signIn() }
        )
    }
}

@Composable
@Preview
fun Banner(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.Black),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp),
            painter = painterResource(id = R.drawable.ic_logo_connect),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(bottom = 16.dp),
            text = stringResource(id = R.string.title_connect_parrot),
            color = Color.White,
            fontSize = 18.sp,
            style = MaterialTheme.typography.body1
        )
    }
}