import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilterFactory.Config> {

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(AuthFilterFactory.Config config) {
        return null;
    }

    public static class Config {
        // Puedes agregar configuración adicional para tu filtro aquí si es necesario
    }
}
