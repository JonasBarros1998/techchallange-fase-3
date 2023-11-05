package com.postech.parquimetro.utils;

import com.postech.parquimetro.aplicacao.exceptions.CondutorDeveTerPeloMenosUmMetodoDePatamentoExcpetion;
import com.postech.parquimetro.aplicacao.exceptions.ConteudoDuplicado;
import com.postech.parquimetro.dominio.exceptions.MetodoDePagamentoInvalido;
import com.postech.parquimetro.dominio.exceptions.PagamentosNaoEncontradoException;
import com.postech.parquimetro.dominio.exceptions.VeiculosNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ValidacaoHandler {
	private final MessageSource messageSource;

	@Value("${app.date.format}")
	private String dateFormat;

	@Autowired
	ValidacaoHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroForm> handler(MethodArgumentNotValidException exception) {
		List<ErroForm> erroFormList = new ArrayList<>();

		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroForm erroForm = new ErroForm(e.getField(), message);
			erroFormList.add(erroForm);
		});
		return erroFormList;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConteudoDuplicado.class)
	public List<ErroForm> handler(ConteudoDuplicado exception) {
		List<ErroForm> erroFormList = new ArrayList<>();
		ErroForm erroForm = new ErroForm(
			"NaoFoiPossivelSalvarOConteudo",
			exception.getMessage());
		erroFormList.add(erroForm);

		return erroFormList;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NoSuchElementException.class)
	public List<ErroForm> handler(NoSuchElementException exception) {
		List<ErroForm> erroFormList = new ArrayList<>();
		ErroForm erroForm = new ErroForm(
			"metodoDePagamento",
			"O campo metodoDePagamento deve conter os seguintes valores: CREDITO, DEBITO ou PIX");
		erroFormList.add(erroForm);

		return erroFormList;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CondutorDeveTerPeloMenosUmMetodoDePatamentoExcpetion.class)
	public List<ErroForm> handler(CondutorDeveTerPeloMenosUmMetodoDePatamentoExcpetion exception) {
		List<ErroForm> erroFormList = new ArrayList<>();
		ErroForm erroForm = new ErroForm(
			"CondutorDeveTerPeloMenosUmMetodoDePatamentoExcpetion",
			exception.getMessage());
		erroFormList.add(erroForm);

		return erroFormList;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(PagamentosNaoEncontradoException.class)
	public List<ErroForm> handler(PagamentosNaoEncontradoException exception) {
		List<ErroForm> erroFormList = new ArrayList<>();
		ErroForm erroForm = new ErroForm(
			"NaoExistePagamentosRegistradosExcetion",
			exception.getMessage());
		erroFormList.add(erroForm);

		return erroFormList;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MetodoDePagamentoInvalido.class)
	public List<ErroForm> handler(MetodoDePagamentoInvalido exception) {
		List<ErroForm> erroFormList = new ArrayList<>();
		ErroForm erroForm = new ErroForm(
			"MetodoDePagamentoInvalido",
			exception.getMessage());
		erroFormList.add(erroForm);

		return erroFormList;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(VeiculosNaoEncontradoException.class)
	public List<ErroForm> handler(VeiculosNaoEncontradoException exception) {
		List<ErroForm> erroFormList = new ArrayList<>();
		ErroForm erroForm = new ErroForm(
			"VeiculosNaoEncontradoException",
			exception.getMessage());
		erroFormList.add(erroForm);

		return erroFormList;
	}

}