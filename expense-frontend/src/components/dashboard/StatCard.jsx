import {
    Card,
    CardContent,
    Typography,
    Box
} from "@mui/material";

function StatCard({
    title,
    value,
    icon,
    color
}) {

    return (

        <Card
            elevation={4}
            sx={{
                borderRadius: 3,
                height: 150,
                transition: ".3s",
                "&:hover": {
                    transform: "translateY(-5px)",
                    boxShadow: 8
                }
            }}
        >

            <CardContent>

                <Box
                    display="flex"
                    justifyContent="space-between"
                    alignItems="center"
                >

                    <Box>

                        <Typography
                            variant="subtitle1"
                            color="text.secondary"
                        >
                            {title}
                        </Typography>

                        <Typography
                            variant="h4"
                            fontWeight="bold"
                            color={color}
                            mt={2}
                        >
                            {value}
                        </Typography>

                    </Box>

                    {icon}

                </Box>

            </CardContent>

        </Card>

    );

}

export default StatCard;