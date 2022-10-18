package uctech.Unimed.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import uctech.Unimed.dtos.*;
import uctech.Unimed.exception.DataNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class DBConection {

    @PersistenceContext
    private EntityManager entityManager;

    public List<BeneficiarioDTO> getBeneficiarioByCpfOrCarteirinha(String cpfOrCarteirinha) {
        String whereClause = "";
        if (cpfOrCarteirinha.length() > 11) {
            whereClause = whereClause.concat("and dbaunimed.f_formata_cartao_dig_num(b.uni_cod_respon," +
                    "       b.bnf_cod_cntrat_cart," +
                    "       b.bnf_cod, b.bnf_cod_depnte) = '").concat(cpfOrCarteirinha).concat("'");
        } else {
            whereClause = whereClause.concat("and trim(pd.doc_nro) = '").concat(cpfOrCarteirinha).concat("'");
        }


        Query query = entityManager.createNativeQuery("select trim(pd.doc_nro) cpf," +
                "       dbaunimed.f_formata_cartao_dig_num(b.uni_cod_respon," +
                "       b.bnf_cod_cntrat_cart," +
                "       b.bnf_cod, b.bnf_cod_depnte) cartao," +
                "       initcap(p.pes_nom_comp) nome," +
                "       p.pes_nom_comp nome_completo," +
                "       trunc(dbaunimed.k_geral.f_verif_null(p.pes_dat_nasc)) data_nascimento," +
                "       trunc(dbaunimed.k_geral.f_verif_null(b.bnf_dat_excl)) data_exclusao," +
                "       trunc(b.bnf_dat_inic_vigen) data_inicio_vigencia," +
                "       substr(trim(dbaunimed.f_busca_mask_param('DM_IND_GRAU_DEPCIA'," +
                "                                                b.bnf_ind_grau_depcia))," +
                "              4) grau_dependencia," +
                "       trim(cv.plano_nro_reg_ans) plano_id," +
                "       p.pes_ind_sexo sexo" +
                "  from dbaunimed.bnfrio       b," +
                "       dbaunimed.pessoa_doc   pd," +
                "       dbaunimed.pessoa       p," +
                "       dbaunimed.cntrat_venda cv" +
                " where b.bnf_cod_pessoa = p.pes_cod " +
                whereClause +
                "   and pd.pes_cod = p.pes_cod" +
                "   and pd.tpdoc_cod = 2" +
                "   and cv.cv_nro = b.cv_nro");

        List<Object[]> rows = query.getResultList();

        List<BeneficiarioDTO> list = new ArrayList<>();

        for (Object[] obj : rows) {
            list.add(new BeneficiarioDTO(
                    (String) obj[0],
                    (String) obj[1],
                    (String) obj[2],
                    (String) obj[3],
                    formataDate(obj[4]),
                    formataDate(obj[5]),
                    formataDate(obj[6]),
                    (String) obj[7],
                    (String) obj[8],
                    (String) obj[9]
            ));
        }

        return list;
    }

    private LocalDate formataDate(Object o) {
        if (!Objects.isNull(o)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(sdf.format((Date) o), formatter);
        }
        return null;
    }

    public BeneficiarioCartaoDTO getBeneficiarioBoleto(String cartao) throws DataNotFoundException {

        Query query = entityManager.createNativeQuery("select dbenef.cartao," +
                " dbenef.nome_completo," +
                " dbenef.grau_dependencia," +
                " dbenef.tipo_contrato," +
                " (select cpf from dbaunimed.v_ud178_dados_beneficiario where cartao = dbenef.cartao_titular), " +
                " dbenef.cartao_titular " +
                " from dbaunimed.v_ud178_dados_beneficiario dbenef" +
                " where 1=1" +
                " and tipo_contrato = 'PF'" +
                " and cartao = '" + cartao.trim() + "'");

        try {
            Object[] row = (Object[]) query.getSingleResult();
            return new BeneficiarioCartaoDTO((String) row[0], (String) row[1], (String) row[2], (String) row[3], (String) row[4], (String) row[5]);

        } catch (Exception e) {
            throw new DataNotFoundException("Beneficiário não encontrado");
        }
    }

    public BigDecimal getBoletosAtrasadosApartir60Dias(String cartao) {

        Query query = entityManager.createNativeQuery("select COUNT(*) " +
                "       from dbaunimed.v_ud178_mobile_fatura mf " +
                "       where mf.usuario_cartao = '" + cartao + "'" +
                "       and (trunc(current_date) - mf.VENCIMENTO_SEGUNDA_VIA) > 59" +
                "       and mf.valor_pago = '0.00'");

        return (BigDecimal) query.getSingleResult();
    }

    public BoletoPathDTO getDadosBoletoPendente(String cartaoTitular) throws DataNotFoundException {

        Query query = entityManager.createNativeQuery("select d.PRCPD_DES_DIR_REDE, d.PRCPD_DES_CAMINH " +
                " from dbaunimed.prcsso_param_doc d " +
                "inner join dbaunimed.prcsso_param_fatura_rec f " +
                " on f.prcp_cod = d.prcp_cod " +
                "where 1=1 " +
                " and d.prcpd_des_dir_oracle = 'DIR_REL' " +
                " and d.prcpd_des like ('%CAPA FATURA CONTRA TITULAR%') " +
                " and f.fr_nro = (select FR_NRO " +
                " from dbaunimed.v_ud178_mobile_fatura " +
                " where usuario_cartao = '" + cartaoTitular + "' " +
                "      and FR_SITUACAO = 'Pendente' " +
                "      and VALOR_PAGO = '0.00') " +
                "order by 2 desc");

        try {
            Object[] row = (Object[]) query.getSingleResult();
            return new BoletoPathDTO((String) row[0], (String) row[1]);

        } catch (Exception e) {
            throw new DataNotFoundException("Boleto não encontrado");
        }


    }


    public List<BoletoDTO> getBoletosAbertosAtrasados(String cartao) {

        Query query = entityManager.createNativeQuery("select b.competencia_segunda_via, b.vencimento_segunda_via, b.valor_segunda_via, b.codigo_barras" +
                "  from dbaunimed.vm_mobile_fatura b " +
                "  where b.usuario_cartao = '" + cartao + "'" +
                "  and b.valor_pago = '0.00'" +
                "  order by b.vencimento_segunda_via desc");

        List<Object[]> rows = query.getResultList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<BoletoDTO> list = new ArrayList<>();


        for (Object[] obj : rows) {
            list.add(new BoletoDTO(
                    (BigDecimal) obj[0],
                    (String) obj[1],
                    (String) obj[2],
                    (String) obj[3]
            ));
        }

        return list;
    }

    public GuiaDTO getStatusGuia(String numeroGuia) {
        Query query = entityManager.createNativeQuery("   select aut.nr_guia NUMERO_GUIA," +
                "       DECODE(aut.cd_situacao," +
                "              1,'Negada'," +
                "              2,'Aprovada'," +
                "              3,'Em estudo'," +
                "              4,'Cancelada'," +
                "              5,'Executada'," +
                "              6,'Aguardando Cancelamento') STATUS_GUIA" +
                "  from datacenter.autsc2_solicitacoes aut" +
                "  where aut.nr_guia = '" + numeroGuia + "'");

        Object[] row = (Object[]) query.getSingleResult();

        if (ObjectUtils.isEmpty(row)) {
            return null;
        }


        return new GuiaDTO((String) row[0], (String) row[1]);
    }

    public List<EmailDTO> getEmailByCpf(String cpf) {

        Query query = entityManager.createNativeQuery("select distinct " +
                "       dbaunimed.f_formata_cartao_dig_num(b.uni_cod_respon,b.bnf_cod_cntrat_cart,b.bnf_cod,b.bnf_cod_depnte) AS cartao_benef," +
                "       pe.pes_nom_comp NOME," +
                "       dbaunimed.f_format_cnpj_cpf('CPF',dbaunimed.f_busca_doc_pessoa(pe.pes_cod,'CPF')) cpf," +
                "       nvl(lower(pec.CON_DES_EMAIL),'Nenhum e-mail cadastrado') EMAIL," +
                "       nvl(lower(pec.con_des_email_altern),'Nenhum e-mail cadastrado') EMAIL_ALTERNATIVO   " +
                "             " +
                "  from dbaunimed.pessoa               pe," +
                "       dbaunimed.bnfrio               b," +
                "       dbaunimed.pessoa_end_contat    pec" +
                " where pec.pes_cod = pe.pes_cod" +
                "       and pec.pes_cod = b.bnf_cod_pessoa " +
                "       and pec.end_ind = 1" +
                "       and dbaunimed.f_busca_doc_pessoa(pe.pes_cod,'CPF') = '" + cpf + "'");


        List<Object[]> rows = query.getResultList();

        List<EmailDTO> list = new ArrayList<>();

        for (Object[] obj : rows) {
            list.add(new EmailDTO(
                    (String) obj[0],
                    (String) obj[1],
                    (String) obj[2],
                    (String) obj[3],
                    (String) obj[4]

            ));
        }

        return list;
    }
}