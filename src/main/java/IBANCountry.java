public class IBANCountry {

    private final String name;
    private final String symbol;
    private final Integer length;
    private final String structure;
    private final String example;
    private final boolean sepa;

    public IBANCountry(String name, String symbol, String length, String structure, String example, String sepa) {
        this.name = name;
        this.symbol = symbol;
        this.length = Integer.parseInt(length);
        this.structure = structure;
        this.example = example;
        this.sepa = sepa.equals("yes");
    }

    public Integer getLength() {
        return length;
    }

    public String getStructure() {
        return structure;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IBANCountry{");
        sb.append("name='").append(name).append('\'');
        sb.append(", symbol='").append(symbol).append('\'');
        sb.append(", length=").append(length);
        sb.append(", structure='").append(structure).append('\'');
        sb.append(", example='").append(example).append('\'');
        sb.append(", sepa=").append(sepa);
        sb.append('}');
        return sb.toString();
    }
}
