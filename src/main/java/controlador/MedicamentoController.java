package controlador;

import dao.MedicamentoDAO;
import java.util.List;
import java.util.Map;
import modelo.Medicamento;

public class MedicamentoController {
    private MedicamentoDAO medicamentoDAO;

    public MedicamentoController() {
        this.medicamentoDAO = new MedicamentoDAO();
    }

    // Listar medicamentos
    public List<Medicamento> listarMedicamentos() {
        return medicamentoDAO.listarMedicamentos();
    }

    // Método para actualizar el stock en base a una compra
    public void actualizarStock(Map<Integer, Integer> carrito) {
        for (Map.Entry<Integer, Integer> entry : carrito.entrySet()) {
            int idMedicamento = entry.getKey();
            int cantidadComprada = entry.getValue();
            medicamentoDAO.actualizarStock(idMedicamento, cantidadComprada);
        }
    }
}
